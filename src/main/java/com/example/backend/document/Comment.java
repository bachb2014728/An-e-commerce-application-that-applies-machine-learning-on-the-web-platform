package com.example.backend.document;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private Product product;
    private String createdById;
    private String content;
    private int rating;
    private boolean status;
    @CreatedDate
    private LocalDateTime datePosted;
    @LastModifiedDate
    private LocalDateTime updatedOn;
    private List<Reply> replies;
    @Data
    @Builder
    public static class Reply {
        @Id
        private String id;
        private String userId;
        private String content;
        private boolean status;
        @CreatedDate
        private LocalDateTime datePosted;
    }
}
