package com.example.backend.dto.comment;

import lombok.Data;

@Data
public class ReplyCreate {
    private String userId;
    private String content;
    private boolean isStoreOwner;
}
