package com.example.scheduler.common.exception;

import com.example.scheduler.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.example.scheduler.common.exception.ErrorCode.VALIDATION_ERROR;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // CustomException을 ErrorResponse로 전송
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomException e, HttpServletRequest request) {
        log.error("예외 발생. ", e);

        ErrorResponse result = new ErrorResponse(e.getErrorCode(), request.getRequestURI(), e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getStatus()).body(result);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("예외 발생. ", e);

        ErrorResponse result = new ErrorResponse(
                VALIDATION_ERROR, request.getRequestURI(),
                VALIDATION_ERROR.getMessage() + e.getFieldError().getDefaultMessage());

        return ResponseEntity.status(VALIDATION_ERROR.getStatus()).body(result);
    }
}
