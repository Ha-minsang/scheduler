package com.example.scheduler.domain.user.repository;

import com.example.scheduler.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(@NotBlank String email);

    Boolean existsByEmail(@NotBlank String email);
}
