package com.example.scheduler.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Schedule 관련 ErrorCode
    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "SCHEDULE_001", "해당 schedule을 찾을 수 없습니다."),
    SCHEDULE_FORBIDDEN(HttpStatus.FORBIDDEN, "SCHEDULE_002", "해당 schedule에 대한 권한이 없습니다."),

    // User 관련 ErrorCode
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER_001", "해당 user를 찾을 수 없습니다."),

    // Comment 관련 ErrorCode
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "COMMENT_001", "해당 comment를 찾을 수 없습니다."),
    COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMENT_002", "해당 comment에 대한 권한이 없습니다."),

    // Auth(인증) 관련 ErrorCode
    AUTH_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_001", "아이디 또는 비밀번호가 올바르지 않습니다."),
    NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED, "AUTH_002", "로그인이 되어 있지 않습니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
