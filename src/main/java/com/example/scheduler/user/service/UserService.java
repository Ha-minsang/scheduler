package com.example.scheduler.user.service;

import com.example.scheduler.schedule.dto.ScheduleCreateRequest;
import com.example.scheduler.schedule.dto.ScheduleCreateResponse;
import com.example.scheduler.schedule.entity.Schedule;
import com.example.scheduler.user.dto.UserCreateRequest;
import com.example.scheduler.user.dto.UserCreateResponse;
import com.example.scheduler.user.entity.User;
import com.example.scheduler.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // CREATE 새 schedule 저장
    @Transactional
    public UserCreateResponse saveUser(@Valid @RequestBody UserCreateRequest request) {
        User user = new User(
                request.getName(),
                request.getEmail()
        );
        User savedUser = userRepository.save(user);
        return new UserCreateResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }


}
