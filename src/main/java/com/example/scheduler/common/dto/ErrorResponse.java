package com.example.scheduler.common.dto;

import com.example.scheduler.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// Error 발생시 전송되는 값을 일정하게 만들기 위한 dto
@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int status;                   // 상태
    private final String code;                  // 커스텀 코드
    private final String message;               // 메세지
    private final String path;                  // 에러 발생 url
    private final LocalDateTime timestamp;      // 에러 발생 시간

    public ErrorResponse(ErrorCode errorCode, String path, String message) {
        this.status = errorCode.getStatus().value();
        this.code = errorCode.getCode();
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}

