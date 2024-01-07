package com.example.farfaraway.model.request;

import lombok.*;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@Getter
public class RegisterRequest {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
    private Boolean isAgree;
}
