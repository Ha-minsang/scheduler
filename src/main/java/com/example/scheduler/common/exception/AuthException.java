package com.example.scheduler.common.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends CustomException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
