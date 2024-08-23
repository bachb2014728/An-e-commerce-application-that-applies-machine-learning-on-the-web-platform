package com.example.backend.dto.comment;

import lombok.Data;

@Data
public class CommentUpdate {
    private String createdById;
    private String content;
    private int rating;
}
