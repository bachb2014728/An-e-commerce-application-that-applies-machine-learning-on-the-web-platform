package com.example.backend.dto.category;

import lombok.Data;

@Data
public class CategoryCreate {
    private String name;
    private String slug;
    private String parentId;
}
