package com.erich.blog.app.dto.auth.request;

import lombok.*;

@Builder
public record SignupRequest(String nombre, String apellido, String email, String password) {
}
