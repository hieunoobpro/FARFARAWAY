package com.example.farfaraway.model.dto;

import com.example.farfaraway.entity.Role;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private List<Role> roles;
}
