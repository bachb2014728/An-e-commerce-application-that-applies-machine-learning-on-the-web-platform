package com.example.backend.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String accountId;
}
