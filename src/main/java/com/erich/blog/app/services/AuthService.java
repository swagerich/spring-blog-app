package com.erich.blog.app.services;

import com.erich.blog.app.dto.auth.LoginDto;
import com.erich.blog.app.dto.auth.RegistroDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String registro(RegistroDto registroDto);

}
