package com.example.scheduler.domain.comment.controller;

import com.example.scheduler.domain.comment.dto.*;
import com.example.scheduler.domain.comment.service.CommentService;
import com.example.scheduler.domain.schedule.dto.ScheduleGetResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scheduler")
public class CommentController {

    private final CommentService commentService;

    // CREATE 새 comment 저장
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentCreateResponse> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CommentCreateRequest request,
            @SessionAttribute("loginUserId") Long loginUserId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.saveComment(scheduleId, loginUserId, request));
    }

    // READ 특정 schedule의 전체 comment 조회
    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<Page<CommentGetResponse>> getComments(
            @PathVariable Long scheduleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByScheduleId(scheduleId, page, pageSize));
    }

    // READ 단일 comment 조회
    @GetMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentGetResponse> getComment(@PathVariable Long scheduleId, @PathVariable Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentById(scheduleId, commentId));
    }

    // UPDATE comment 수정
    @PutMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid@RequestBody CommentUpdateRequest request,
            @SessionAttribute("loginUserId") Long loginUserId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(scheduleId, commentId, loginUserId, request));
    }

    // DELETE comment 삭제
    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @SessionAttribute("loginUserId") Long loginUserId
    ) {
        commentService.deleteComment(scheduleId, commentId, loginUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
