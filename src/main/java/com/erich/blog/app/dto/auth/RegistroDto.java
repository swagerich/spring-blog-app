package com.erich.blog.app.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroDto {

    private String nombre;

    private String apellido;

    private String email;

    private String password;
}
