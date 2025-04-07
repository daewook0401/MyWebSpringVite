package com.myspring.myproject.auth.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myspring.myproject.auth.service.AuthService;
import com.myspring.myproject.token.model.service.TokenService;
import com.myspring.myproject.user.model.dto.UserDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO member){
        Map<String, String> loginResponse = authService.login(member);
        // log.info("토큰 {}", loginResponse);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> token){
        String refreshToken = token.get("refreshToken");
        Map<String, String> newToken = tokenService.refreshToken(refreshToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(newToken);
    }
}
