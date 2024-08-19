package com.example.backend.dto.seller;

import com.example.backend.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SellerDto {
    private String id;
    private String name;
    private String slug;
    private String imageId;
    private String address;
    private boolean uniqueIdentifier;
    private boolean status;
    private LocalDateTime createdAt;
    private String description;
    private UserDto createBy;
}
