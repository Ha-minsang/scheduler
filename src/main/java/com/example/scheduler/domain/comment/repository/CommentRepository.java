package com.example.scheduler.domain.comment.repository;

import com.example.scheduler.domain.comment.entity.Comment;
import com.example.scheduler.domain.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Schedule이 같은 List<Comment> 찾기
    Page<Comment> findAllByScheduleId(Long scheduleId, Pageable pageable);

    // Schedule과 Id가 같은 Comment 찾기
    Comment findByScheduleIdAndId(Long scheduleId, Long id);

    List<Comment> findAllByUserId(Long userId);

    List<Comment> findAllByScheduleId(Long scheduleId);

    Long countByScheduleId(Long scheduleId);
}
