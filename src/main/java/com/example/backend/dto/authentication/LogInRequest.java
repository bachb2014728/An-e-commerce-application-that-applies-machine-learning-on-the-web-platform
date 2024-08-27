package com.example.backend.dto.authentication;

import lombok.Data;

@Data
public class LogInRequest {
    private String email;
    private String password;
}
