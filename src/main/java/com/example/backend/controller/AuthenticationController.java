package com.example.backend.controller;

import com.example.backend.dto.LogInRequest;
import com.example.backend.dto.LogInResponse;
import com.example.backend.dto.SignUpRequest;
import com.example.backend.security.CustomUserDetails;
import com.example.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<CustomUserDetails> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponse> logIn(@RequestBody LogInRequest logInRequest) {
        return ResponseEntity.ok(authenticationService.logIn(logInRequest));
    }
}
