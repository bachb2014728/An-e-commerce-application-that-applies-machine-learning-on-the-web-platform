package com.example.backend.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentItem {
    private String id;
    private String productId;
    private String createdById;
    private String content;
    private int rating;
    private boolean status;
    private LocalDateTime datePosted;
}
