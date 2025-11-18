package com.example.scheduler.domain.schedule.service;

import com.example.scheduler.common.config.AuthManager;
import com.example.scheduler.common.exception.ScheduleException;
import com.example.scheduler.common.exception.UserException;
import com.example.scheduler.domain.comment.entity.Comment;
import com.example.scheduler.domain.comment.repository.CommentRepository;
import com.example.scheduler.domain.schedule.dto.*;
import com.example.scheduler.domain.schedule.entity.Schedule;
import com.example.scheduler.domain.schedule.repository.ScheduleRepository;
import com.example.scheduler.domain.user.entity.User;
import com.example.scheduler.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import static com.example.scheduler.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthManager authManager;

    // CREATE 새 schedule 저장
    @Transactional
    public ScheduleCreateResponse createSchedule(@Valid @RequestBody ScheduleCreateRequest request, Long loginUserId) {
        User user = getUserById(loginUserId);
        Schedule schedule = new Schedule(
                user,
                request.getTitle(),
                request.getContents()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return ScheduleCreateResponse.from(savedSchedule);
    }

    // READ 전체 schedule 조회 (page 0부터 시작, default page = 0, default pageSize = 10)
    @Transactional(readOnly = true)
    public Page<ScheduleGetResponse> getAllSchedules(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("modifiedAt").descending());
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);
        return schedules.map(ScheduleGetResponse::from);
    }

    // READ userId로 해당 userId가 작성한 schedule 조회
    @Transactional(readOnly = true)
    public Page<ScheduleGetResponse> getSchedulesByUser(Long userId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("modifiedAt").descending());
        getUserById(userId); // user 존재 여부 확인용
        Page<Schedule> schedules = scheduleRepository.findAllByUserId(userId, pageable);
        return schedules.map(ScheduleGetResponse::from);
    }

    // READ scheduleId로 단일 schedule 조회
    @Transactional(readOnly = true)
    public ScheduleGetResponse getOneSchedule(Long scheduleId) {
        Schedule schedule = getScheduleById(scheduleId);
        return ScheduleGetResponse.from(schedule);
    }

    // UPDATE schedule 수정
    @Transactional
    public ScheduleUpdateResponse updateSchedule(Long scheduleId, Long loginUserId, ScheduleUpdateRequest request) {
        Schedule schedule = getScheduleById(scheduleId);
        Long userId = schedule.getUser().getId();
        authManager.validateAuthorization(loginUserId, userId);
        schedule.setSchedule(
                request.getTitle(),
                request.getContents()
        );
        return ScheduleUpdateResponse.from(schedule);
    }

    // DELETE schedule 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId, Long loginUserId) {
        Schedule schedule = getScheduleById(scheduleId);
        Long userId = schedule.getUser().getId();
        authManager.validateAuthorization(loginUserId, userId);
        commentRepository.findAllByScheduleId(scheduleId).forEach(Comment::softDelete); // 삭제할 일정에 있는 댓글 먼저 softDelete 진행
        scheduleRepository.delete(schedule);
    }

    // scheduleId가 일치하는 schedule 가져오기
    // scheduleID가 일치하는 schedule이 없으면 예외 처리
    private Schedule getScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleException(NOT_FOUND_SCHEDULE)
        );
        return schedule;
    }

    // userId가 일치하는 user 가져오기
    // userId가 일치하는 user가 없으면 예외 처리
    private User getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(NOT_FOUND_USER)
        );
        return user;
    }
}
