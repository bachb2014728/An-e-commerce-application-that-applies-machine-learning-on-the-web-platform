package com.example.backend.dto.condition;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConditionDto {
    private String id;
    private String name;
    private String normalizedName;
    private boolean status;
}
