package com.example.backend.dto.category;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CategoryDetail {
    private String id;
    private String name;
    private String slug;
    private String parentId;
    private List<CategoryItem> childes;
}
