package com.example.backend.service;

import com.example.backend.dto.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    SignUpResponse signUp(SignUpRequest signUpRequest);

    LogInResponse logIn(LogInRequest logInRequest);

    ProfileResponse profile(UserDetails userDetails);
}
