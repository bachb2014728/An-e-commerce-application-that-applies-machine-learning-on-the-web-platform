package com.example.backend.dto.seller;

import lombok.Data;

@Data
public class SellerUpdate {
    private String name;
    private String slug;
    private String imageId;
    private String address;
    private String description;
}
