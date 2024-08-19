package com.example.backend.controller;

import com.example.backend.dto.ApiObject;
import com.example.backend.dto.seller.SellerCreate;
import com.example.backend.dto.seller.SellerDto;
import com.example.backend.dto.seller.SellerItem;
import com.example.backend.dto.seller.SellerUpdate;
import com.example.backend.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sellers")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
    @GetMapping("")
    public ResponseEntity<List<SellerItem>> getAll(){
        return ResponseEntity.ok(sellerService.getAll());
    }
    @PostMapping("")
    public ResponseEntity<SellerDto> create(@RequestBody SellerCreate sellerCreate){
        return ResponseEntity.ok(sellerService.create(sellerCreate));
    }
    @GetMapping("/{id}")
    public ResponseEntity<SellerDto> getOne(@PathVariable String id){
        return ResponseEntity.ok(sellerService.getOne(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<SellerDto> update(@PathVariable String id, @RequestBody SellerUpdate sellerUpdate){
        return ResponseEntity.ok(sellerService.update(id,sellerUpdate));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiObject> delete(@PathVariable String id){
        return ResponseEntity.ok(sellerService.delete(id));
    }
}
