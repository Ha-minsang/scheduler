package com.example.scheduler.schedule.service;

import com.example.scheduler.schedule.dto.ScheduleCreateRequest;
import com.example.scheduler.schedule.dto.ScheduleCreateResponse;
import com.example.scheduler.schedule.entity.Schedule;
import com.example.scheduler.schedule.repository.ScheduleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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
}
