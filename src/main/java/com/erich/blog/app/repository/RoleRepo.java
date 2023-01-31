package com.erich.blog.app.repository;

import com.erich.blog.app.entity.auth.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepo  extends CrudRepository<Role,Long> {

    Optional<Role> findByAuthority(String authority);
}
