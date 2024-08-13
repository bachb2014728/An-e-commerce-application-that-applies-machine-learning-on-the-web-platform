package com.example.backend.service;

import com.example.backend.dto.ApiObject;
import com.example.backend.dto.category.*;

import java.util.List;

public interface CategoryService {
    List<CategoryItem> getAll();

    CategoryDto create(CategoryCreate categoryCreate);

    CategoryDetail getOne(String id);

    CategoryDetail update(String id, CategoryUpdate categoryUpdate);

    ApiObject delete(String id);

    List<CategoryDetail> getAllDepthOne();
}
