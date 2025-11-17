package com.example.scheduler.domain.schedule.controller;

import com.example.scheduler.domain.schedule.dto.*;
import com.example.scheduler.domain.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<ScheduleCreateResponse>createSchedule(
            @Valid @RequestBody ScheduleCreateRequest request,
            @SessionAttribute("loginUserId") Long loginUserId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(request, loginUserId));
    }

    // READ 전체 schedule 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleGetResponse>> findAllSchedules(){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllSchedules());
    }

    // READ Login한 user가 작성한 schedule 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleGetResponse>> findSchedulesByLoginUser(@SessionAttribute("loginUserId") Long loginUserId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getSchedulesByUser(loginUserId));
    }

    // READ userId가 일치하는 schedule 조회
    @GetMapping("/users/{userId}/schedules")
    public ResponseEntity<List<ScheduleGetResponse>> findSchedulesByUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getSchedulesByUser(userId));
    }

    // READ schedule 단일 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleGetResponse> findSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOneSchedule(scheduleId));
    }

    // UPDATE schedule 수정
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequest request,
            @SessionAttribute("loginUserId") Long loginUserId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(scheduleId, loginUserId, request));
    }

    // DELETE schedule 삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute("loginUserId") Long loginUserId
    ) {
        scheduleService.deleteSchedule(scheduleId, loginUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
