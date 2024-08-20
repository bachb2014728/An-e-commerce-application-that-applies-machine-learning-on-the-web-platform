package com.example.backend.dto.product;

import com.example.backend.dto.category.CategoryItem;
import com.example.backend.dto.manufacturer.ManufacturerItem;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProductItem {
    private String id;
    private String name;
    private String slug;
    private double price;
    private int quantity;
    private String imageId;
    private List<String> tags;
    private String sellerId;
}
