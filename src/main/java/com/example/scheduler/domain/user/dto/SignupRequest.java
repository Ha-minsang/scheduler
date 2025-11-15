package com.example.scheduler.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank
    private String userName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
