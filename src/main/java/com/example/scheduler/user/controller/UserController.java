package com.example.scheduler.user.controller;

import com.example.scheduler.user.dto.UserCreateRequest;
import com.example.scheduler.user.dto.UserCreateResponse;
import com.example.scheduler.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
