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
    private String size;
    private String color;
    private String material;
    private double weight;
    //tình trạng : mới,đã qua sử dung, đã sửa chữa , likenew
    private String condition;
    private List<String> tags;
    private String sellerId;
}
