package com.example.backend.controller;

import com.example.backend.dto.ApiObject;
import com.example.backend.dto.category.*;
import com.example.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("")
    public ResponseEntity<List<CategoryItem>> getAllCategory(){
        List<CategoryItem> items = categoryService.getAll();
        return ResponseEntity.ok(items);
    }
    @GetMapping("/depth=1")
    public ResponseEntity<List<CategoryDetail>> getAllCategoryWithDepthEqualOne(){
        List<CategoryDetail> items = categoryService.getAllDepthOne();
        return ResponseEntity.ok(items);
    }
    @PostMapping("")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryCreate categoryCreate){
        return ResponseEntity.ok(categoryService.create(categoryCreate));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDetail> getOne(@PathVariable String id){
        return ResponseEntity.ok(categoryService.getOne(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDetail> update(@PathVariable String id, @RequestBody CategoryUpdate categoryUpdate){
        return ResponseEntity.ok(categoryService.update(id,categoryUpdate));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiObject> delete(@PathVariable String id){
        return ResponseEntity.ok(categoryService.delete(id));
    }
}
