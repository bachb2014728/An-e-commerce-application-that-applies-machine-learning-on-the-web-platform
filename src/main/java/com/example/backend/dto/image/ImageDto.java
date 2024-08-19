package com.example.backend.dto.image;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {
    private String id;
    private String name;
    private String type;
    private byte[] imageData;
}
