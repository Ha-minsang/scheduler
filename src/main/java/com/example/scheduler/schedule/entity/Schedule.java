package com.example.scheduler.schedule.entity;

import com.example.scheduler.global.entity.BaseEntity;
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

    // schedule 작성자
    @Column(nullable = false)
    private String writer;

    // schedule 제목
    @Column(length = 30, nullable = false)
    private String title;

    // schedule 내용
    @Column(length = 200, nullable = false)
    private String contents;

    public Schedule(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public void setSchedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
