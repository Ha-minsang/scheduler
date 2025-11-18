package com.example.scheduler.domain.comment.service;

import com.example.scheduler.common.config.AuthManager;
import com.example.scheduler.common.exception.CommentException;
import com.example.scheduler.common.exception.ScheduleException;
import com.example.scheduler.common.exception.UserException;
import com.example.scheduler.domain.comment.dto.*;
import com.example.scheduler.domain.comment.entity.Comment;
import com.example.scheduler.domain.comment.repository.CommentRepository;
import com.example.scheduler.domain.schedule.entity.Schedule;
import com.example.scheduler.domain.schedule.repository.ScheduleRepository;
import com.example.scheduler.domain.user.entity.User;
import com.example.scheduler.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.scheduler.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final AuthManager authManager;

    // CREATE 새 comment 저장
    @Transactional
    public CommentCreateResponse saveComment(Long scheduleId, Long loginUserId, CommentCreateRequest request) {
        User user = getUserById(loginUserId);
        Schedule schedule = getScheduleById(scheduleId);
        Comment comment = new Comment(
                schedule,
                user,
                request.getContents()
        );
        Comment savedComment = commentRepository.save(comment);
        return CommentCreateResponse.from(savedComment);
    }

    // READ scheduleId가 일치하는 전체 comment 조회
    @Transactional(readOnly = true)
    public Page<CommentGetResponse> getAllCommentsByScheduleId(Long scheduleId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("modifiedAt").descending());
        Page<Comment> comments = commentRepository.findAllByScheduleId(scheduleId, pageable);
        return comments.map(CommentGetResponse::from);
    }

    // READ commentId로 단건 comment 조회
    public CommentGetResponse getCommentById(Long scheduleId, Long commentId) {
        Comment comment = getCommentByScheduleAndId(scheduleId, commentId);
        return CommentGetResponse.from(comment);
    }

    // UPDATE comment 수정
    @Transactional
    public CommentUpdateResponse updateComment(Long scheduleId, Long commentId, Long loginUserId, CommentUpdateRequest request) {
        Comment comment = getCommentByScheduleAndId(scheduleId, commentId);
        Long userId = comment.getUser().getId();
        authManager.validateAuthorization(loginUserId, userId);
        comment.setComment(
                request.getContents()
        );
        return CommentUpdateResponse.from(comment);
    }

    // DELETE comment 삭제
    @Transactional
    public void deleteComment(Long scheduleId, Long commentId, Long loginUserId) {
        Comment comment = getCommentByScheduleAndId(scheduleId, commentId);
        Long userId = comment.getUser().getId();
        authManager.validateAuthorization(loginUserId, userId);
        commentRepository.delete(comment);
    }

    // scheduleId가 일치하는 일정을 가져오기, 일치하는 schedule이 없으면 예외처리
    private Schedule getScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleException(NOT_FOUND_SCHEDULE)
        );
        return schedule;
    }

    // userId와 일치하는 user을 가져오기, 일치하는 user가 없으면 예외처리
    private User getUserById(Long loginUserId) {
        User user = userRepository.findById(loginUserId).orElseThrow(
                () -> new UserException(NOT_FOUND_USER)
        );
        return user;
    }

    // scheduleId와 commentId가 일치하는 일정이 없으면 예외 처리
    private Comment getCommentByScheduleAndId(Long scheduleId, Long commentId) {
        Comment comment = commentRepository.findByScheduleIdAndId(scheduleId, commentId);
        if (comment == null) {
            throw new CommentException(NOT_FOUND_COMMENT);
        }
        return comment;
    }
}




