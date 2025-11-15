package com.example.scheduler.domain.user.repository;

import com.example.scheduler.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByModifiedAtDesc();

    Optional<User> findByEmail(@NotBlank String email);
}
