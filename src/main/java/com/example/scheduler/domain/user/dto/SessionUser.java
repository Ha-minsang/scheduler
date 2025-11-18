package com.example.scheduler.domain.user.dto;

import com.example.scheduler.domain.user.entity.User;
import lombok.Getter;

@Getter
public class SessionUser {

    private final Long id;
    private final String userName;
    private final String email;
    private final String password;

    public SessionUser(Long id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public static SessionUser from(User user) {
        return new SessionUser(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
