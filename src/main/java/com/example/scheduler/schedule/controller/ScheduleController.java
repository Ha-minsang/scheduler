package com.example.scheduler.schedule.controller;

import com.example.scheduler.schedule.dto.*;
import com.example.scheduler.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scheduler")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // CREATE 새 schedule 저장
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleCreateResponse>createSchedule(@Valid @RequestBody ScheduleCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.saveSchedule(request));
    }

    // READ 작성자가 일치하는 schedule 조회
    // 작성자 미입력시 전체 schedule 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleGetResponse>> getSchedulesByWriter(@RequestParam(required = false) String writer) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findSchedule(writer));
    }

    // READ schedule 단일 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleGetResponse> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOneSchedule(scheduleId));
    }

    // UPDATE schedule 수정
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> updateSchedule(@PathVariable Long scheduleId, @Valid @RequestBody ScheduleUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(scheduleId, request));
    }

    // DELETE schedule 수정
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
