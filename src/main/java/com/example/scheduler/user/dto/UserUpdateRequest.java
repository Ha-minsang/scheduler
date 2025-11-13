package com.example.scheduler.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @NotBlank
    private String userName;
    @NotBlank
    private String email;
}
