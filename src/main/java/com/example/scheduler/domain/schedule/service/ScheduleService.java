package com.example.scheduler.domain.schedule.service;

import com.example.scheduler.common.exception.AuthException;
import com.example.scheduler.common.exception.ScheduleException;
import com.example.scheduler.common.exception.UserException;
import com.example.scheduler.domain.schedule.dto.*;
import com.example.scheduler.domain.schedule.entity.Schedule;
import com.example.scheduler.domain.schedule.repository.ScheduleRepository;
import com.example.scheduler.domain.user.entity.User;
import com.example.scheduler.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static com.example.scheduler.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

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
        return new ScheduleCreateResponse(
                savedSchedule.getId(),
                user.getUserName(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    // READ 전체 schedule 조회
    @Transactional(readOnly = true)
    public List<ScheduleGetResponse> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
        List<ScheduleGetResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleGetResponse dto = new ScheduleGetResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContents(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    // READ userId로 해당 userId가 작성한 schedule 조회
    @Transactional(readOnly = true)
    public List<ScheduleGetResponse> getSchedulesByUser(Long userId) {
        User user = getUserById(userId);
        List<Schedule> schedules = scheduleRepository.findAllByUserIdOrderByModifiedAtDesc(userId);
        List<ScheduleGetResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleGetResponse dto = new ScheduleGetResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContents(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    // READ scheduleId로 단일 schedule 조회
    @Transactional(readOnly = true)
    public ScheduleGetResponse getOneSchedule(Long scheduleId) {
        Schedule schedule = getScheduleById(scheduleId);
        return new ScheduleGetResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // UPDATE schedule 수정
    @Transactional
    public ScheduleUpdateResponse updateSchedule(Long scheduleId, Long loginUserId, ScheduleUpdateRequest request) {
        Schedule schedule = getScheduleById(scheduleId);
        validateAuthorization(loginUserId, schedule);
        schedule.setSchedule(
                request.getTitle(),
                request.getContents()
        );
        return new ScheduleUpdateResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // DELETE schedule 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId, Long loginUserId) {
        Schedule schedule = getScheduleById(scheduleId);
        validateAuthorization(loginUserId, schedule);
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

    // 로그인한 유저가 권한이 있는지 확인
    private void validateAuthorization(Long loginUserId, Schedule schedule) {
        boolean isSameUser = schedule.getUser().getId().equals(loginUserId);
        if (!isSameUser) {
            throw new ScheduleException(SCHEDULE_FORBIDDEN);
        }
    }
}
