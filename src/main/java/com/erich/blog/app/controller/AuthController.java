package com.erich.blog.app.controller;

import com.erich.blog.app.dto.auth.request.RefreshTokenRequest;
import com.erich.blog.app.dto.auth.response.JwtResponse;
import com.erich.blog.app.dto.auth.request.LoginRequest;
import com.erich.blog.app.dto.auth.request.SignupRequest;
import com.erich.blog.app.entity.auth.RefreshToken;
import com.erich.blog.app.entity.auth.User;
import com.erich.blog.app.security.CustomUserDetailsService;
import com.erich.blog.app.security.jwt.JwtTokenProvider;
import com.erich.blog.app.services.impl.AuthServiceImpl;
import com.erich.blog.app.services.impl.RefreshTokenServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    private final CustomUserDetailsService customUserDetailsService;

    private final RefreshTokenServiceImpl refreshTokenService;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthServiceImpl authService, CustomUserDetailsService customUserDetailsService, RefreshTokenServiceImpl refreshTokenService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.customUserDetailsService = customUserDetailsService;
        this.refreshTokenService = refreshTokenService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse login = authService.login(loginRequest);
        return new ResponseEntity<>(login,HttpStatus.OK);
    }

    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<?> registro(@RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(authService.registro(signupRequest), HttpStatus.CREATED);
    }

    @GetMapping("/current-user")
    public User currentUser(Principal principal){
       UserDetails userDetails = customUserDetailsService.loadUserByUsername(principal.getName());
       return (User)userDetails;
    }

   /* @PostMapping(value = {"/register-admin","/signup-admin"})
    public ResponseEntity<?> registroAdmin(@RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(authService.regitroAdmin(signupRequest), HttpStatus.CREATED);
    }*/

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.token())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userInfo -> {
                    String accessToken = jwtTokenProvider.generateToken(customUserDetailsService.loadUserByUsername(userInfo.getUsername()));
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .tokenType(refreshTokenRequest.token())
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }

    @GetMapping("/existsName")
    public ResponseEntity<Boolean> existsName(@RequestParam String name){
        return new ResponseEntity<>(authService.existsName(name),HttpStatus.OK);
    }

    @GetMapping("/existsMail")
    public ResponseEntity<Boolean> existsMail(@RequestParam String mail){
        return new ResponseEntity<>(authService.existsMail(mail),HttpStatus.OK);
    }

}
