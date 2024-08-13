package com.example.backend.config;

import com.example.backend.document.Account;
import com.example.backend.document.Role;
import com.example.backend.document.User;
import com.example.backend.exception.error.NotFoundException;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@DependsOn("initRoles")
public class InitAccountsConfig {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    @Bean
    public CommandLineRunner initAccounts(PasswordEncoder passwordEncoder){
        return args -> {
            Role role = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy quyền quản trị viên : ADMIN"));
            if (!accountRepository.existsAccountByEmail("adminNo1@gmail.com")) {

                Account account1 = Account.builder()
                        .email("adminNo1@gmail.com")
                        .password(passwordEncoder.encode("admin123"))
                        .roles(Collections.singletonList(role))
                        .build();
                accountRepository.save(account1);
                User userOfAdmin1 = User.builder().firstName("Admin").lastName("No.1").account(account1).build();
                userRepository.save(userOfAdmin1);
            }
            if (!accountRepository.existsAccountByEmail("adminNo2@gmail.com")) {
                Account account2 = Account.builder()
                        .email("adminNo2@gmail.com")
                        .password(passwordEncoder.encode("admin123"))
                        .roles(Collections.singletonList(role))
                        .build();
                accountRepository.save(account2);
                User userOfAdmin2 = User.builder().firstName("Admin").lastName("No.2").account(account2).build();
                userRepository.save(userOfAdmin2);
            }
        };
    }
}
