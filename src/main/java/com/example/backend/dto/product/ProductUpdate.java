package com.example.backend.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductUpdate {
    private String name;
    private String slug;
    private double price;
    private int quantity;
    private String categoryId;
    private String manufacturerId;
    private List<String> images;
    private String size;
    private String color;
    private String material;
    private double weight;
    private String condition;
    private List<String> tags;
}
