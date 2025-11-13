package com.example.scheduler.schedule.repository;

import com.example.scheduler.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // writer가 일치하는 schedule을 조회하여 수정 시간 순으로 정렬
    List<Schedule> findAllByUserIdOrderByModifiedAtDesc(Long userId);

    // 전체 schedule 조회하여 수정 시간 순으로 정렬
    List<Schedule> findAllByOrderByModifiedAtDesc();
}
