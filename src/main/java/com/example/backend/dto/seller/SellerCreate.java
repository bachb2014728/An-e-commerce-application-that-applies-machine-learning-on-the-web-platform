package com.example.backend.dto.seller;

import lombok.Data;

@Data
public class SellerCreate {
    private String name;
    private String slug;
    private String address;
    private String description;
    private String userId;
}
