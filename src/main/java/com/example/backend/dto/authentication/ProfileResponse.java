package com.example.backend.dto.authentication;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
public class ProfileResponse {
    private String name;
    private String email;
    private Collection<? extends GrantedAuthority> roles;
    private boolean isEnable;
}
