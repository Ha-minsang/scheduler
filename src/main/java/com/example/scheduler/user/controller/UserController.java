package com.example.scheduler.user.controller;

import com.example.scheduler.user.dto.*;
import com.example.scheduler.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scheduler")
public class UserController {

    private final UserService userService;

    // CREATE 새 user 정보 저장
    @PostMapping("/users")
    public ResponseEntity<UserCreateResponse> createSchedule(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(request));
    }

    // READ 작성자가 일치하는 schedule 조회
    // 작성자 미입력시 전체 schedule 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserGetResponse>> getSchedulesByWriter(@RequestParam(required = false) String name) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(name));
    }

    // READ schedule 단일 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserGetResponse> getSchedule(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOneUser(userId));
    }

    // UPDATE schedule 수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserUpdateResponse> updateSchedule(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, request));
    }

}
