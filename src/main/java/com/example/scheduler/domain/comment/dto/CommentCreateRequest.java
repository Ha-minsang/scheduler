package com.example.scheduler.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotBlank(message = "이메일이 누락되었습니다.")
    private String contents;
}
