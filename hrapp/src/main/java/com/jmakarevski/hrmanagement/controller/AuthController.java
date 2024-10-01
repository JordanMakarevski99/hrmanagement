package com.jmakarevski.hrmanagement.controller;

import com.jmakarevski.hrmanagement.dto.LoginDto;
import com.jmakarevski.hrmanagement.security.AuthResponse;
import com.jmakarevski.hrmanagement.security.JwtTokenProvider;
import com.jmakarevski.hrmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        String token = userService.authenticate(loginDto.getUsername(), loginDto.getPassword());
        if (token != null) {
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
