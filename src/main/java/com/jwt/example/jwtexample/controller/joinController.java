package com.jwt.example.jwtexample.controller;

import com.jwt.example.jwtexample.entity.UserRequestDto;
import com.jwt.example.jwtexample.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class joinController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody UserRequestDto requestDto) {
        log.info("userName: {}, password: {}",requestDto.getUsername(), requestDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(userService.join(requestDto));
    }
}