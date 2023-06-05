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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public JwtResponse login(LoginRequest loginRequest) throws Exception {
        this.auth(loginRequest.username(), loginRequest.password());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.username());
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.username());
            return JwtResponse.builder()
                    .accessToken(jwtTokenProvider.generateToken(userDetails))
                    .refreshToken(refreshToken.getToken())
                    .tokenType("Bearer ").build();
//        return jwtTokenProvider.generateToken(authenticate);
    }
        @Override
        public String registro (SignupRequest signupRequest){
            this.validationFieldSignup(signupRequest);
            User user = User.builder()
                    .nombre(signupRequest.nombre())
                    .apellido(signupRequest.apellido())
                    .email(signupRequest.email())
                    .password(passwordEncoder.encode(signupRequest.password()))
                    .build();
            Set<Role> roles = new HashSet<>();
            Optional<Role> userRole;
            if (signupRequest.nombre().contains("admin")) {
                userRole = Optional.of(roleRepo.findByAuthority("ROLE_ADMIN").orElseThrow(() -> new NotFoundException("Role admin no encontrado!")));
                Role role = userRole.get();
                roles.add(role);
                user.setRoles(roles);
            } else {
                userRole = Optional.of(roleRepo.findByAuthority("ROLE_USER").orElseThrow(() -> new NotFoundException("Role user no encontrado!")));
                Role role = userRole.get();
                roles.add(role);
                user.setRoles(roles);
            }
            userRepo.save(user);

            return "Registro con exito";
        }

        private void validationFieldSignup (SignupRequest signupRequest){
            if (userRepo.existsByNombre(signupRequest.nombre())) {
                throw new BadRequestException("Error: " + signupRequest.nombre() + "ya esta en uso!");
            }
            if (userRepo.existsByEmail(signupRequest.email())) {
                throw new BadRequestException("Error:  " + signupRequest.email() + " ya esta en uso!");
            }
        }

        @Transactional(readOnly = true)
        public boolean existsName (String user){
            User userName = userRepo.findByNombre(user).orElseThrow(() -> new NotFoundException("Usuario no existe!"));
            return userRepo.existsByNombre(userName.getNombre());
        }

        @Transactional(readOnly = true)
        public boolean existsMail (String mail){
            User userMail = userRepo.findByEmail(mail).orElseThrow(() -> new NotFoundException("Email no existe!"));
            return userRepo.existsByEmail(userMail.getEmail());
        }
        private void auth (String username, String password) throws Exception {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            } catch (DisabledException e) {
                throw new Exception("User DISABLED" + e.getMessage());
            } catch (BadCredentialsException e) {
                throw new Exception("Invalid credentials" + e.getMessage());
            }
        }
    }
