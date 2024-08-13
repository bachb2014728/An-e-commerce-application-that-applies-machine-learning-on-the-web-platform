package com.example.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiObject {
    private String message;
    private String type;
}
