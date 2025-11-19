package com.example.scheduler.domain.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleCreateRequest {

    @NotBlank(message = "제목이 누락되었습니다.")
    private String title;

    @NotBlank(message = "내용이 누락되었습니다.")
    private String contents;
}
