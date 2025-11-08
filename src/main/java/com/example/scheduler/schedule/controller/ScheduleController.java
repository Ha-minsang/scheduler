package com.example.scheduler.schedule.controller;

import com.example.scheduler.schedule.dto.ScheduleCreateRequest;
import com.example.scheduler.schedule.dto.ScheduleCreateResponse;
import com.example.scheduler.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // CREATE 새 schedule 저장
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleCreateResponse>createSchedule(@Valid @RequestBody ScheduleCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.saveSchedule(request));
    }
}
