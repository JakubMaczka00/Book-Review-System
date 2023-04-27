package com.example.book_review_system.controller;

import com.example.book_review_system.dto.request.LoginReqDTO;
import com.example.book_review_system.dto.request.RegisterReqDTO;
import com.example.book_review_system.dto.response.AuthResDTO;
import com.example.book_review_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResDTO> register(
            @RequestBody RegisterReqDTO request)
    {
        return ResponseEntity.ok(authService.register(request));

    }
    @PostMapping("/login")
    public ResponseEntity<AuthResDTO> login(
            @RequestBody LoginReqDTO request)
    {
        return ResponseEntity.ok(authService.login(request));

    }




}
