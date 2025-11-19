package com.example.scheduler.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank(message = "유저명이 누락되었습니다.")
    @Size(max = 10, message = "유저명은 최대 10자까지 가능합니다.")
    private String userName;

    @NotBlank(message = "이메일이 누락되었습니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호가 누락되었습니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6~20자리여야 합니다.")
    private String password;
}
