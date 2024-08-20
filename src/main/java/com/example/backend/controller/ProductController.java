package com.example.backend.controller;

import com.example.backend.dto.ApiObject;
import com.example.backend.dto.product.ProductCreate;
import com.example.backend.dto.product.ProductDto;
import com.example.backend.dto.product.ProductItem;
import com.example.backend.dto.product.ProductUpdate;
import com.example.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("")
    public ResponseEntity<List<ProductItem>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }
    @PostMapping("")
    public ResponseEntity<ProductDto> create(@RequestBody ProductCreate productCreate){
        return ResponseEntity.ok(productService.create(productCreate));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable String id){
        return ResponseEntity.ok(productService.getOne(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable String id, @RequestBody ProductUpdate productUpdate){
        return ResponseEntity.ok(productService.update(id,productUpdate));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiObject> delete(@PathVariable String id){
        return ResponseEntity.ok(productService.delete(id));
    }
}
