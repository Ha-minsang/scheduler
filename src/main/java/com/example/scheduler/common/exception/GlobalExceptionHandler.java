package com.example.scheduler.common.exception;

import com.example.scheduler.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.example.scheduler.common.exception.ErrorCode.NOT_LOGGED_IN;

@ControllerAdvice
public class GlobalExceptionHandler {

    // CustomException을 ErrorResponse로 전송
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomException e, HttpServletRequest request) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(new ErrorResponse(e.getErrorCode(), request.getRequestURI()));
    }

    // @SessionAttribute에서 로그인이 안되어 있을시 발생하는 HttpSessionRequiredException 예외를 ErrorResponse 형태로 전송
    @ExceptionHandler(HttpSessionRequiredException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpSessionRequiredException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(NOT_LOGGED_IN, request.getRequestURI()));
    }
}
