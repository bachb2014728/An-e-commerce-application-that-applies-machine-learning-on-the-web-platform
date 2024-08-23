package com.example.backend.dto.product;

import com.example.backend.document.Color;
import com.example.backend.dto.category.CategoryItem;
import com.example.backend.dto.comment.CommentDto;
import com.example.backend.dto.condition.ConditionDto;
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
    private List<Color> colors;
    private List<CommentDto> comments;
    private double rating;
    private String description;
    private double weight;
    private boolean status;
    private ConditionDto condition;
    private List<String> tags;
    private LocalDateTime createdAt;
    private String sellerId;
}
