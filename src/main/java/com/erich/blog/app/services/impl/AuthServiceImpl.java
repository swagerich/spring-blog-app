package com.erich.blog.app.services.impl;

import com.erich.blog.app.dto.auth.LoginDto;
import com.erich.blog.app.dto.auth.RegistroDto;
import com.erich.blog.app.entity.auth.Role;
import com.erich.blog.app.entity.auth.User;
import com.erich.blog.app.exception.BadRequestException;
import com.erich.blog.app.repository.RoleRepo;
import com.erich.blog.app.repository.UserRepo;
import com.erich.blog.app.security.jwt.JwtTokenProvider;
import com.erich.blog.app.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public String login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getNombreOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return jwtTokenProvider.generateToken(authenticate);
    }

    @Override
    public String registro(RegistroDto registroDto) {
        if (userRepo.existsByNombre(registroDto.getNombre())) {
            throw new BadRequestException("Nombre ya existe !!");
        }
        if (userRepo.existsByEmail(registroDto.getEmail())) {
            throw new BadRequestException("Email ya existe !!");
        }
        User user = User.builder()
                .nombre(registroDto.getNombre())
                .apellido(registroDto.getApellido())
                .email(registroDto.getEmail())
                .password(passwordEncoder.encode(registroDto.getPassword()))
                .build();
        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepo.findByAuthority("ROLE_USER");
        if(userRole.isPresent()){
            roles.add(userRole.get());
            user.setRoles(roles);
        }
        userRepo.save(user);

        return "Registro con exito";
    }
}
