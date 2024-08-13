package com.example.backend.service.Impl;

import com.example.backend.document.Account;
import com.example.backend.document.Role;
import com.example.backend.document.User;
import com.example.backend.dto.LogInRequest;
import com.example.backend.dto.LogInResponse;
import com.example.backend.dto.SignUpRequest;
import com.example.backend.exception.error.AlreadyExistException;
import com.example.backend.exception.error.NotFoundException;
import com.example.backend.jwt.JwtService;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.CustomUserDetails;
import com.example.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    @Override
    public CustomUserDetails signUp(SignUpRequest signUpRequest) {
        if (accountRepository.findAccountByEmail(signUpRequest.getEmail()).isPresent()){
            throw new AlreadyExistException("There is an account with that email address: " + signUpRequest.getEmail());
        }
        if (!roleRepository.existsRoleByName("USER")){
            Role roleCreate = Role.builder()
                    .name("USER")
                    .build();
            roleRepository.save(roleCreate);
        }
        Role role = roleRepository.findByName("USER").orElseThrow(
                ()-> new NotFoundException("Không tìm thấy quyền người dùng : USER"));
        Account account = Account.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(Collections.singletonList(role))
                .build();
        Account accountNew = accountRepository.save(account);

        User user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .account(accountNew)
                .build();
        userRepository.save(user);

        return new CustomUserDetails(account);
    }

    @Override
    public LogInResponse logIn(LogInRequest logInRequest) {
        Account account = accountRepository.findAccountByEmail(logInRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Email hoặc mật khẩu không đúng!"));

        manager.authenticate(new UsernamePasswordAuthenticationToken(logInRequest.getEmail(),logInRequest.getPassword()));

        var jwt = jwtService.generateToken(new CustomUserDetails(account));
        LogInResponse response = new LogInResponse();
        response.setToken(jwt.getCode());

        return response;
    }
}
