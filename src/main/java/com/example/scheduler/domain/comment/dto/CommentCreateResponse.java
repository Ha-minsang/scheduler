package com.example.scheduler.domain.comment.dto;

import java.time.LocalDateTime;

public class CommentCreateResponse {

    private final Long id;
    private final String userName;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentCreateResponse(Long id, String userName, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userName = userName;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
