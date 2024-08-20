package com.example.backend.dto.product;

import com.example.backend.dto.category.CategoryItem;
import com.example.backend.dto.manufacturer.ManufacturerItem;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProductDto {
    private String id;
    private String name;
    private String slug;
    private double price;
    private int quantity;
    private CategoryItem category;
    private ManufacturerItem manufacturer;
    private List<String> images;
    private String size;
    private String color;
    private String material;
    private double weight;
    private boolean status;
    private String condition;
    private List<String> tags;
    private LocalDateTime createdAt;
    private String sellerId;
}
