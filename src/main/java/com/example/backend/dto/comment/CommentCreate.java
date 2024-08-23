package com.example.backend.dto.comment;

import lombok.Data;

@Data
public class CommentCreate {
    private String productId;
    private String createdById;
    private String content;
    private int rating;
    private boolean isStoreOwner;
}
