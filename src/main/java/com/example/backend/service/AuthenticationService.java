package com.example.backend.service;

import com.example.backend.dto.LogInRequest;
import com.example.backend.dto.LogInResponse;
import com.example.backend.dto.SignUpRequest;
import com.example.backend.security.CustomUserDetails;

public interface AuthenticationService {
    CustomUserDetails signUp(SignUpRequest signUpRequest);

    LogInResponse logIn(LogInRequest logInRequest);
}
