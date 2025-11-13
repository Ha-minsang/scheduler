package com.example.scheduler.schedule.entity;

import com.example.scheduler.common.entity.BaseEntity;
import com.example.scheduler.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedules_id")
    private Long id;

    // schedule 제목
    @Column(length = 30, nullable = false)
    private String title;

    // schedule 내용
    @Column(length = 200, nullable = false)
    private String contents;

    // 다대일 관계
    @ManyToOne
    @JoinColumn(name = "users_id", nullable=false)
    private User user;

    public Schedule(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public void setSchedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
