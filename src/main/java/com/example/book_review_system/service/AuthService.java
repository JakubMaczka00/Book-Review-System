package com.example.book_review_system.service;

import com.example.book_review_system.component.Role;
import com.example.book_review_system.config.jwt.JwtService;
import com.example.book_review_system.dto.request.LoginReqDTO;
import com.example.book_review_system.dto.request.RegisterReqDTO;
import com.example.book_review_system.dto.response.AuthResDTO;
import com.example.book_review_system.entity.User;
import com.example.book_review_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResDTO register(RegisterReqDTO registerReqDTO){
        User user = User.builder()
                .firstname(registerReqDTO.getFirstname())
                .lastname(registerReqDTO.getLastname())
                .email(registerReqDTO.getEmail())
                .password(passwordEncoder.encode(registerReqDTO.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResDTO.builder()
                .token(jwtToken)
                .build();

    }
    public AuthResDTO login(LoginReqDTO loginReqDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginReqDTO.getEmail(),
                        loginReqDTO.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginReqDTO.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResDTO.builder()
                .token(jwtToken)
                .build();

    }
}
