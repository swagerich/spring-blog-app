package com.erich.blog.app.repository;

import com.erich.blog.app.entity.auth.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNombreOrEmail(String nombre, String email);

    Optional<User> findByNombre(String nombre);

    Boolean existsByNombre(String nombre);

    Boolean existsByEmail(String email);
}
