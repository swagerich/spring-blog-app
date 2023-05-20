package com.erich.blog.app.services.impl;

import com.erich.blog.app.entity.auth.RefreshToken;
import com.erich.blog.app.repository.RefreshTokenRepo;
import com.erich.blog.app.repository.UserRepo;
import com.erich.blog.app.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.jwt.refreshTokenDurationMs}")
    private Long refreshTokenDurationMs;
    private final UserRepo userRepo;

    private final RefreshTokenRepo refreshTokenRepo;

    @Override
    public RefreshToken createRefreshToken(String username) {
        return RefreshToken.builder()
                .user(userRepo.findByNombre(username).orElseThrow())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
