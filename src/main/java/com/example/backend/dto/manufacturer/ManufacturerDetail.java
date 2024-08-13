package com.example.backend.dto.manufacturer;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ManufacturerDetail {
    private String id;
    private String name;
    private String slug;
}
