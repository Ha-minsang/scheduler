package com.example.scheduler.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @NotBlank(message = "유저명 누락되었습니다.")
    private String userName;

    @NotBlank(message = "이메일이 누락되었습니다.")
    @Email
    private String email;
}
