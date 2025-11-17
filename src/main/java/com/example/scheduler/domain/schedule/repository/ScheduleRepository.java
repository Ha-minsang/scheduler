package com.example.scheduler.domain.schedule.repository;

import com.example.scheduler.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByUserId(Long userId);

    List<Schedule> findAllByOrderByModifiedAtDesc();
}
