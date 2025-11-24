package com.example.scheduler.domain.schedule.repository;

import com.example.scheduler.domain.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findAllByUserId(Long userId, Pageable pageable);

    Page<Schedule> findAll(Pageable pageable);

    List<Schedule> findAllByUserId(Long userId);
}
