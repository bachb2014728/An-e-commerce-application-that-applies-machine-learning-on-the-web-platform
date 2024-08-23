package com.example.backend.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommentDto {
    private String id;
    private String productId;
    private CreatedCommentBy commentBy;
    private String content;
    private int rating;
    private boolean status;
    private LocalDateTime datePosted;
    private List<Reply> replies;
    @Data
    @Builder
    public static class Reply {
        private String id;
        private CreatedCommentBy commentBy;
        private String content;
        private LocalDateTime datePosted;
    }
}
