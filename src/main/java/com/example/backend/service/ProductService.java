package com.example.backend.service;

import com.example.backend.dto.ApiObject;
import com.example.backend.dto.product.ProductCreate;
import com.example.backend.dto.product.ProductDto;
import com.example.backend.dto.product.ProductItem;
import com.example.backend.dto.product.ProductUpdate;

import java.util.List;

public interface ProductService {
    List<ProductItem> getAll();

    ProductDto create(ProductCreate productCreate);

    ProductDto getOne(String id);

    ProductDto update(String id, ProductUpdate productUpdate);

    ApiObject delete(String id);
}
