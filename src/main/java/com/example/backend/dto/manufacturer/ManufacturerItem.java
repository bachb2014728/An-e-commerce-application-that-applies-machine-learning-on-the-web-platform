package com.example.backend.dto.manufacturer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManufacturerItem {
    private String id;
    private String name;
    private String slug;
}
