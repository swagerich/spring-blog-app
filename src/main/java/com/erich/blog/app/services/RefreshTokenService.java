package com.erich.blog.app.services;

import com.erich.blog.app.entity.auth.RefreshToken;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);
}
