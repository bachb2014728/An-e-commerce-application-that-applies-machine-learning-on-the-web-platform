package com.example.backend.service;

import com.example.backend.dto.ApiObject;
import com.example.backend.dto.seller.SellerCreate;
import com.example.backend.dto.seller.SellerDto;
import com.example.backend.dto.seller.SellerItem;
import com.example.backend.dto.seller.SellerUpdate;

import java.util.List;

public interface SellerService {
    List<SellerItem> getAll();

    SellerDto create(SellerCreate sellerCreate);

    SellerDto getOne(String id);

    SellerDto update(String id, SellerUpdate sellerUpdate);

    ApiObject delete(String id);
}
