package com.example.backend.dto.product;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductCreate {
    private String name;
    private String slug;
    private double price;
    private int quantity;
    private String categoryId;
    private String manufacturerId;
    private List<String> images;
    private List<String> colors;
    private String description;
    private double weight;
    private String conditionId;
    private List<String> tags;
    private String sellerId;
}
