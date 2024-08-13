package com.example.backend.dto.category;

import lombok.Data;

@Data
public class CategoryUpdate {
    private String name;
    private String slug;
    private String parentId;
}
