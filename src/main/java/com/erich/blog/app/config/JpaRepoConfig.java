package com.erich.blog.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.erich.blog.app.repository")
public class JpaRepoConfig {
}
