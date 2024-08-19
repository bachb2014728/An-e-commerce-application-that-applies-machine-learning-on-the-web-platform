package com.example.backend.dto.seller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SellerItem {
    private String id;
    private String name;
    private boolean status;
    private String slug;
    private boolean uniqueIdentifier;
}
