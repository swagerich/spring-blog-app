package com.erich.blog.app;

import com.erich.blog.app.entity.auth.Role;
import com.erich.blog.app.repository.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringBlogAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBlogAppApplication.class, args);
	}

	private final RoleRepo roleRepo;

	public SpringBlogAppApplication(RoleRepo roleRepo) {
		this.roleRepo = roleRepo;
	}

	@Override
	public void run(String... args)  {

		/*Set<Role> roles = new HashSet<>();
		Role roleAdmin = new Role();
		roleAdmin.setAuthority("ROLE_ADMIN");
		Role roleUser = new Role();
		roleUser.setAuthority("ROLE_USER");

		roles.add(roleUser);
		roles.add(roleAdmin);
		roleRepo.saveAll(roles);*/
	}
}
