package com.example.backend.service.Impl;

import com.example.backend.document.Account;
import com.example.backend.document.Role;
import com.example.backend.document.User;
import com.example.backend.dto.authentication.*;
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
import org.springframework.security.core.userdetails.UserDetails;
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
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
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
        CustomUserDetails customUserDetails = new CustomUserDetails(account);
        return SignUpResponse.builder()
                .name(user.getFullName())
                .email(account.getEmail())
                .roles(customUserDetails.getAuthorities())
                .token(jwtService.generateToken(customUserDetails).getCode())
                .build();
    }

    @Override
    public LogInResponse logIn(LogInRequest logInRequest) {
        Account account = accountRepository.findAccountByEmail(logInRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Email hoặc mật khẩu không đúng!"));

        manager.authenticate(new UsernamePasswordAuthenticationToken(logInRequest.getEmail(),logInRequest.getPassword()));
        User user = userRepository.findByAccount(account);
        CustomUserDetails customUserDetails = new CustomUserDetails(account);
        return LogInResponse.builder()
                .name(user.getFullName())
                .email(account.getEmail())
                .roles(customUserDetails.getAuthorities())
                .token(jwtService.generateToken(customUserDetails).getCode())
                .build();
    }

    @Override
    public ProfileResponse profile(UserDetails userDetails) {
        Account account = accountRepository.findAccountByEmail(userDetails.getUsername())
                .orElseThrow(()->new NotFoundException("Không tìm thấy tài khoản nào với : "+userDetails.getUsername()));
        User user = userRepository.findByAccount(account);
        return ProfileResponse.builder()
                .email(userDetails.getUsername())
                .name(user.getFullName())
                .roles(userDetails.getAuthorities())
                .isEnable(userDetails.isEnabled())
                .build();
    }
}
