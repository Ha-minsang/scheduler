package com.example.scheduler.common.exception;

public class ScheduleException extends CustomException {

    public ScheduleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
