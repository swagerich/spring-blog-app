package com.erich.blog.app.controller;

import com.erich.blog.app.dto.auth.JwtResponse;
import com.erich.blog.app.dto.auth.LoginDto;
import com.erich.blog.app.dto.auth.RegistroDto;
import com.erich.blog.app.services.impl.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<?> registro(@RequestBody RegistroDto registroDto) {
        return new ResponseEntity<>(authService.registro(registroDto), HttpStatus.CREATED);
    }
}
