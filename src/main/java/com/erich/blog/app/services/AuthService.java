package com.erich.blog.app.services;

import com.erich.blog.app.dto.auth.response.JwtResponse;
import com.erich.blog.app.dto.auth.request.LoginRequest;
import com.erich.blog.app.dto.auth.request.SignupRequest;

public interface AuthService {

    JwtResponse login(LoginRequest loginRequest);

    String registro(SignupRequest signupRequest);

}
