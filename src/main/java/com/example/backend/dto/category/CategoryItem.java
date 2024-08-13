package com.example.backend.dto.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryItem {
    private String id;
    private String name;
    private String slug;
}
