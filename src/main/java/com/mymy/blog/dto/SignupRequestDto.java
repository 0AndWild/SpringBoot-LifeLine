package com.mymy.blog.dto;

import lombok.Getter;
import lombok.Setter;

//회원가입 요청 DTO
@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}