package com.erich.blog.app.services.impl;

import com.erich.blog.app.dto.auth.response.JwtResponse;
import com.erich.blog.app.dto.auth.request.LoginRequest;
import com.erich.blog.app.dto.auth.request.SignupRequest;
import com.erich.blog.app.entity.auth.RefreshToken;
import com.erich.blog.app.entity.auth.Role;
import com.erich.blog.app.entity.auth.User;
import com.erich.blog.app.exception.BadRequestException;
import com.erich.blog.app.exception.NotFoundException;
import com.erich.blog.app.repository.RoleRepo;
import com.erich.blog.app.repository.UserRepo;
import com.erich.blog.app.security.CustomUserDetailsService;
import com.erich.blog.app.security.jwt.JwtTokenProvider;
import com.erich.blog.app.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    private final RefreshTokenServiceImpl refreshTokenService;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        if (authenticate.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.username());
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.username());
            return JwtResponse.builder()
                    .accessToken(jwtTokenProvider.generateToken(userDetails))
                    .refreshToken(refreshToken.getToken())
                    .tokenType("Bearer ").build();
//        return jwtTokenProvider.generateToken(authenticate);
        } else {
            throw new UsernameNotFoundException("¡Solicitud de usuario inválida!");
        }
    }

    @Override
    public String registro(SignupRequest signupRequest) {
        this.validationFieldSignup(signupRequest);
        User user = User.builder()
                .nombre(signupRequest.nombre())
                .apellido(signupRequest.apellido())
                .email(signupRequest.email())
                .password(passwordEncoder.encode(signupRequest.password()))
                .build();
        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepo.findByAuthority("ROLE_USER");
        if (userRole.isPresent()) {
            roles.add(userRole.get());
            user.setRoles(roles);
        }
        userRepo.save(user);

        return "Registro con exito";
    }

    public String regitroAdmin(SignupRequest signupRequest) {
        this.validationFieldSignup(signupRequest);
        User user = User.builder()
                .nombre(signupRequest.nombre())
                .email(signupRequest.email())
                .apellido(signupRequest.apellido())
                .password(passwordEncoder.encode(signupRequest.password()))
                .build();
        Set<Role> roles = new HashSet<>();
        Role roleAdmin = roleRepo.findByAuthority("ROLE_ADMIN").orElseThrow(() -> new NotFoundException("ROLE ADMIN NO ENCONTRADA!"));
        roles.add(roleAdmin);
        user.setRoles(roles);
        userRepo.save(user);
        return "Administrador " + user.getUsername() + " registrado con exito";
    }

    private void validationFieldSignup(SignupRequest signupRequest) {
        if (userRepo.existsByNombre(signupRequest.nombre())) {
            throw new BadRequestException("Error: " + signupRequest.nombre() + "ya esta en uso!");
        }
        if (userRepo.existsByEmail(signupRequest.email())) {
            throw new BadRequestException("Error:  " + signupRequest.email() + " ya esta en uso!");
        }
    }
}
