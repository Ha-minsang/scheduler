package com.example.scheduler.domain.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String contents;
}
