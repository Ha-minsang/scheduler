package com.example.scheduler.user.repository;

import com.example.scheduler.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByModifiedAtDesc();

    List<User> findAllByUserNameOrderByModifiedAtDesc(String name);

    Optional<User> findByEmail(@NotBlank String email);

    Optional<User> findByEmailAndPassword(@NotBlank String email, @NotBlank String password);
}
