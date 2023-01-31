package com.erich.blog.app.security;

import com.erich.blog.app.entity.auth.User;
import com.erich.blog.app.repository.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String nombreOrEmail) throws UsernameNotFoundException {
        User user = userRepo.findByNombreOrEmail(nombreOrEmail, nombreOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con nombre o email"));
        Set<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(u -> new SimpleGrantedAuthority(u.getAuthority()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(nombreOrEmail,user.getPassword(),authorities);
    }
}
