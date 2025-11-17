package com.example.scheduler.domain.user.controller;

import com.example.scheduler.common.exception.UserException;
import com.example.scheduler.domain.user.dto.*;
import com.example.scheduler.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.scheduler.common.exception.ErrorCode.NOT_LOGGED_IN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scheduler")
public class UserController {

    private final UserService userService;

    // CREATE user 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> createUser(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    // login 로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        SessionUser sessionUser = userService.login(request);
        session.setAttribute("loginUserId", sessionUser.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // logout 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if (sessionUser == null) {
            throw new UserException(NOT_LOGGED_IN);
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // READ 전체 user 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserGetResponse>> findAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsers());
    }

    // READ user 단일 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserGetResponse> findUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOneUser(userId));
    }

    // UPDATE user 수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequest request,
            HttpSession session
    ) {
        Long loginUserId = (Long) session.getAttribute("loginUserId");
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, loginUserId, request));
    }

    // DELETE user 삭제
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId,
            HttpSession session
    ) {
        Long loginUserId = (Long) session.getAttribute("loginUserId");
        userService.deleteUser(userId, loginUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
