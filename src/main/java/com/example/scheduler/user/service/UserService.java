package com.example.scheduler.user.service;

import com.example.scheduler.user.dto.*;
import com.example.scheduler.user.entity.User;
import com.example.scheduler.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // CREATE 새 schedule 저장
    @Transactional
    public SignupResponse saveUser(@Valid @RequestBody SignupRequest request) {
        User user = new User(
                request.getUserName(),
                request.getEmail(),
                request.getPassword()
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
        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("없는 유저 입니다.")
        );
        return new SessionUser(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    // READ userName 입력시 일치하는 user 조회
    // 미입력시 전체 user 조회
    @Transactional(readOnly = true)
    public List<UserGetResponse> findUser(String userName) {
        if (userName == null) { // writer 미입력시 전체 일정 조회
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
        } else { // userName 입력시 userName 일치하는 일정 조회
            List<User> users = userRepository.findAllByUserNameOrderByModifiedAtDesc(userName);
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
    public UserUpdateResponse updateUser(Long userId, UserUpdateRequest request) {
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

    // userId와 일치하는 user 가져오기
    // userID가 일치하는 일정이 없으면 예외 처리
    private User getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("없는 유저 입니다.")
        );
        return user;
    }
}
