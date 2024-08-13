package com.example.backend.controller;

import com.example.backend.dto.ApiObject;
import com.example.backend.dto.manufacturer.ManufacturerCreate;
import com.example.backend.dto.manufacturer.ManufacturerDetail;
import com.example.backend.dto.manufacturer.ManufacturerItem;
import com.example.backend.dto.manufacturer.ManufacturerUpdate;
import com.example.backend.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {
    private final ManufacturerService manufacturerService;
    @GetMapping("")
    public ResponseEntity<List<ManufacturerItem>> getAllManufacturer(){
        return ResponseEntity.ok(manufacturerService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDetail> getOne(@PathVariable String id){
        return ResponseEntity.ok(manufacturerService.getOne(id));
    }
    @PostMapping("")
    public ResponseEntity<ManufacturerItem> create(@RequestBody ManufacturerCreate manufacturerCreate){
        return ResponseEntity.ok(manufacturerService.create(manufacturerCreate));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerDetail> update(@PathVariable String id,
                                                     @RequestBody ManufacturerUpdate manufacturerUpdate){
        return ResponseEntity.ok(manufacturerService.update(manufacturerUpdate,id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ApiObject> delete(@PathVariable String id){
        return ResponseEntity.ok(manufacturerService.delete(id));
    }
}
