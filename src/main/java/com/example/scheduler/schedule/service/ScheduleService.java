package com.example.scheduler.schedule.service;

import com.example.scheduler.schedule.dto.*;
import com.example.scheduler.schedule.entity.Schedule;
import com.example.scheduler.schedule.repository.ScheduleRepository;
import com.example.scheduler.user.entity.User;
import com.example.scheduler.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // CREATE 새 schedule 저장
    @Transactional
    public ScheduleCreateResponse createSchedule(@Valid @RequestBody ScheduleCreateRequest request, Long loginUserId) {
        User user = userRepository.findById(loginUserId).orElseThrow(
                () -> new IllegalArgumentException("없는 유저 입니다.")
        );
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
    public List<ScheduleGetResponse> getSchedulesByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("없는 유저 입니다.")
        );
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
    public ScheduleUpdateResponse updateSchedule(Long scheduleId, ScheduleUpdateRequest request) {
        Schedule schedule = getScheduleById(scheduleId);
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
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = getScheduleById(scheduleId);
        scheduleRepository.delete(schedule);
    }

    // scheduleId가 일치하는 schedule 가져오기
    // scheduleID가 일치하는 schedule이 없으면 예외 처리
    private Schedule getScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("일정이 없습니다.")
        );
        return schedule;
    }
}
