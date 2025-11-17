package com.example.scheduler.domain.comment.entity;

import com.example.scheduler.common.entity.BaseEntity;
import com.example.scheduler.domain.schedule.entity.Schedule;
import com.example.scheduler.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    private Long id;

    // comment 내용
    @Column(length = 200, nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedules_id", nullable = false)
    private Schedule schedule;

    public Comment(Schedule schedule, User user, String contents) {
        this.schedule = schedule;
        this.user = user;
        this.contents = contents;
    }

    public void setComment(String contents) {
        this.contents = contents;
    }
}
