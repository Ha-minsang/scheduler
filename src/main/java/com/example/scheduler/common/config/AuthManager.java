package com.example.scheduler.common.config;

import com.example.scheduler.common.exception.AuthException;
import com.example.scheduler.common.exception.UserException;
import org.springframework.stereotype.Component;

import static com.example.scheduler.common.exception.ErrorCode.ACCESS_DENIED;
import static com.example.scheduler.common.exception.ErrorCode.NOT_LOGGED_IN;

@Component
public class AuthManager {

    public void validateLogin(Long loginUserId) {
        if (loginUserId == null) {
            throw new UserException(NOT_LOGGED_IN);
        }
    }

    public void validateAuthorization(Long loginUserId, Long userId) {
        boolean isSameUser = userId.equals(loginUserId);
        if (!isSameUser) {
            throw new AuthException(ACCESS_DENIED);
        }
    }
}
