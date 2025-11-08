package com.example.scheduler.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleCreateRequest {

    @NotBlank
    private String writer;
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
}
