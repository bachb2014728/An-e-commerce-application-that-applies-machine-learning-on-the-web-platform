package com.example.backend.dto;

import lombok.Data;

@Data
public class LogInRequest {
    private String email;
    private String password;
}
