package com.example.scheduler.user.repository;

import com.example.scheduler.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
