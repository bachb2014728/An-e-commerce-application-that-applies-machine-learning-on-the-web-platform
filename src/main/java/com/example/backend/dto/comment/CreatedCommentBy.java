package com.example.backend.dto.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatedCommentBy {
    private String name;
    private String avatar;
    private boolean isStoreOwner;
}
