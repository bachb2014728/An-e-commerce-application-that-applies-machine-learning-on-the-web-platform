package com.example.backend.service;

import com.example.backend.dto.ApiObject;
import com.example.backend.dto.manufacturer.ManufacturerCreate;
import com.example.backend.dto.manufacturer.ManufacturerDetail;
import com.example.backend.dto.manufacturer.ManufacturerItem;
import com.example.backend.dto.manufacturer.ManufacturerUpdate;

import java.util.List;

public interface ManufacturerService {
    List<ManufacturerItem> getAll();

    ManufacturerDetail getOne(String id);

    ManufacturerItem create(ManufacturerCreate manufacturerCreate);

    ManufacturerDetail update(ManufacturerUpdate manufacturerUpdate, String id);

    ApiObject delete(String id);
}
