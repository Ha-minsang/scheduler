package com.example.scheduler.domain.comment.repository;

import com.example.scheduler.domain.comment.entity.Comment;
import com.example.scheduler.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Schedule이 같은 List<Comment> 찾기
    List<Comment> findAllBySchedule(Schedule schedule);

    // Schedule과 Id가 같은 Comment 찾기
    Comment findByScheduleAndId(Schedule schedule, Long id);

    // 해당 일정에 작성되어있는 댓글 개수 조회
    Long countBySchedule(Schedule schedule);
}
