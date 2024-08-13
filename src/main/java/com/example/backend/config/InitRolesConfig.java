package com.example.backend.config;

import com.example.backend.document.Role;
import com.example.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitRolesConfig {
    private final RoleRepository roleRepository;
    @Bean
    public CommandLineRunner initRoles() {
        return args -> {
            if(!roleRepository.existsRoleByName("ADMIN")){
                Role role1 = Role.builder().name("ADMIN").build();
                roleRepository.save(role1);
            }
            if (!roleRepository.existsRoleByName("USER")){
                Role role2 = Role.builder().name("USER").build();
                roleRepository.save(role2);
            }
            if (!roleRepository.existsRoleByName("SELLER")){
                Role role3 = Role.builder().name("SELLER").build();
                roleRepository.save(role3);
            }
        };
    }
}
