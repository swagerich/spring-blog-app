package com.erich.blog.app.security.jwt;

import com.erich.blog.app.exception.BadRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value(value = "${app.jwt.secret-key}")
    private String secret;

    @Value(value = "${app.jwt.expiration}")
    private Long expiration;


    //generacion de jwt
    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        Date currentDate = new Date();

        Date expirateDate = new Date(currentDate.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirateDate)
                .signWith(key())
                .compact();
    }

    //get username from jwt token
    public String getUsername(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }


    //validate jwt token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new BadRequestException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new BadRequestException("Expiration JWT token");
        } catch (UnsupportedJwtException e) {
            throw new BadRequestException("Unsuported JWT token");
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Jwt Claims String is empty ");
        }
    }
}
