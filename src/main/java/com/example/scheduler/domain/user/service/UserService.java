package com.example.scheduler.domain.user.service;

import com.example.scheduler.common.config.AuthManager;
import com.example.scheduler.common.config.PasswordEncoder;
import com.example.scheduler.common.exception.AuthException;
import com.example.scheduler.common.exception.CommentException;
import com.example.scheduler.common.exception.UserException;
import com.example.scheduler.domain.comment.entity.Comment;
import com.example.scheduler.domain.comment.repository.CommentRepository;
import com.example.scheduler.domain.schedule.entity.Schedule;
import com.example.scheduler.domain.schedule.repository.ScheduleRepository;
import com.example.scheduler.domain.user.dto.*;
import com.example.scheduler.domain.user.entity.User;
import com.example.scheduler.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static com.example.scheduler.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthManager authManager;

    // CREATE 새 schedule 저장
    @Transactional
    public SignupResponse createUser(@Valid @RequestBody SignupRequest request) {
        User user = new User(
                request.getUserName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        User savedUser = userRepository.save(user);
        return new SignupResponse(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }

    // login 로그인
    @Transactional(readOnly = true)
    public SessionUser login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UserException(NOT_FOUND_USER)
        );
        boolean isMatched = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isMatched) {
            throw new AuthException(AUTH_INVALID_CREDENTIALS);
        }
        return new SessionUser(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword()
        );
    }


    // READ 전체 user 조회
    public List<UserGetResponse> findAllUsers() {
        List<User> users = userRepository.findAllByOrderByModifiedAtDesc();
        List<UserGetResponse> dtos = new ArrayList<>();
        for (User user : users) {
            UserGetResponse dto = new UserGetResponse(
                    user.getId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    // READ 단일 user 조회
    @Transactional(readOnly = true)
    public UserGetResponse findOneUser(Long userId) {
        User user = getUserById(userId);
        return new UserGetResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    // UPDATE user 수정
    @Transactional
    public UserUpdateResponse updateUser(Long userId, Long loginUserId, UserUpdateRequest request) {
        authManager.validateLogin(loginUserId);
        authManager.validateAuthorization(loginUserId, userId);
        User user = getUserById(userId);
        user.setUser(
                request.getUserName(),
                request.getEmail()
        );
        return new UserUpdateResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    // DELETE user 삭제
    @Transactional
    public void deleteUser(Long userId, Long loginUserId) {
        authManager.validateLogin(loginUserId);
        authManager.validateAuthorization(loginUserId, userId);
        User user = getUserById(userId);
        commentRepository.findAllByUserId(userId).forEach(Comment::softDelete);
        scheduleRepository.findAllByUserId(userId).forEach(Schedule::softDelete);
        userRepository.delete(user);
    }

    // userId와 일치하는 user 가져오기
    // userID가 일치하는 일정이 없으면 예외 처리
    private User getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(NOT_FOUND_USER)
        );
        return user;
    }
}
