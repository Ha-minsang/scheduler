package com.example.scheduler.schedule.service;

import com.example.scheduler.schedule.dto.ScheduleCreateRequest;
import com.example.scheduler.schedule.dto.ScheduleCreateResponse;
import com.example.scheduler.schedule.dto.ScheduleGetResponse;
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
        } else { // writer 입력시 writer가 일치하는 일정 조회
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
       Schedule schedule = checkSchedule(scheduleId);
       return new ScheduleGetResponse(
               schedule.getId(),
               schedule.getWriter(),
               schedule.getTitle(),
               schedule.getContents(),
               schedule.getCreatedAt(),
               schedule.getModifiedAt()
       );
    }

    // scheduleID가 일치하는 일정이 없으면 예외 처리
    private Schedule checkSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("일정이 없습니다.")
        );
        return schedule;
    }
}
