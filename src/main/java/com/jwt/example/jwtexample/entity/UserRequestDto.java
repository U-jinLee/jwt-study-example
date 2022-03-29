package com.jwt.example.jwtexample.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    private String username;
    private String password;
    private String roles; // USER, ADMIN의 방식으로 저장
}