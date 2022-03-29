package com.jwt.example.jwtexample.controller;

import com.jwt.example.jwtexample.entity.UserRequestDto;
import com.jwt.example.jwtexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class RestApiController {
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    @PostMapping("/token")
    public String token() {
        return "token";
    }
}
