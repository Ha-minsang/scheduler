package com.example.scheduler.domain.comment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {

    @NotBlank(message = "내용이 누락되었습니다.")
    private String contents;
}
