package com.example.scheduler.domain.user.dto;

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
}
