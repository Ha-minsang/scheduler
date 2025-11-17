package com.example.scheduler.domain.comment.dto;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {

    @Valid
    private String contents;
}
