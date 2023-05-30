package com.erich.blog.app.dto;

import com.erich.blog.app.entity.auth.User;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class UserDto {

    private Long id;

    private String nombre;

    public static UserDto fromEntity(User user){
        if(user != null){
            return UserDto.builder()
                    .id(user.getId())
                    .nombre(user.getNombre()).build();
        }
        return null;
    }
}
