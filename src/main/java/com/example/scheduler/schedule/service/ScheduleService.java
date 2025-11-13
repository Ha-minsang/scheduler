package com.example.scheduler.schedule.service;

import com.example.scheduler.schedule.dto.*;
import com.example.scheduler.schedule.entity.Schedule;
import com.example.scheduler.schedule.repository.ScheduleRepository;
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

    // CREATE 새 schedule 저장
    @Transactional
    public ScheduleCreateResponse saveSchedule(@Valid @RequestBody ScheduleCreateRequest request) {
        Schedule schedule = new Schedule(
                request.getWriter(),
                request.getTitle(),
                request.getContents()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleCreateResponse(
                savedSchedule.getId(),
                savedSchedule.getWriter(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    // READ writer 입력시 일치하는 schedule 조회
    // 미입력시 전체 schedule 조회
    @Transactional(readOnly = true)
    public List<ScheduleGetResponse> findSchedule(String writer) {
        if (writer == null) { // writer 미입력시 전체 일정 조회
            List<Schedule> schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
            List<ScheduleGetResponse> dtos = new ArrayList<>();
            for (Schedule schedule : schedules) {
                ScheduleGetResponse dto = new ScheduleGetResponse(
                        schedule.getId(),
                        schedule.getWriter(),
                        schedule.getTitle(),
                        schedule.getContents(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                );
                dtos.add(dto);
            }
            return dtos;
        } else { // writer 입력시 writer가 일치하는 schedule 조회
            List<Schedule> schedules = scheduleRepository.findAllByWriterOrderByModifiedAtDesc(writer);
            List<ScheduleGetResponse> dtos = new ArrayList<>();
            for (Schedule schedule : schedules) {
                ScheduleGetResponse dto = new ScheduleGetResponse(
                        schedule.getId(),
                        schedule.getWriter(),
                        schedule.getTitle(),
                        schedule.getContents(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                );
                dtos.add(dto);
            }
            return dtos;
        }
    }

    // READ 단일 schedule 조회
    @Transactional(readOnly = true)
    public ScheduleGetResponse findOneSchedule(Long scheduleId) {
       Schedule schedule = getScheduleById(scheduleId);
       return new ScheduleGetResponse(
               schedule.getId(),
               schedule.getWriter(),
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
                schedule.getWriter(),
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
