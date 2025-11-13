package com.example.scheduler.user.repository;

import com.example.scheduler.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByModifiedAtDesc();

    List<User> findAllByNameOrderByModifiedAtDesc(String name);
}
