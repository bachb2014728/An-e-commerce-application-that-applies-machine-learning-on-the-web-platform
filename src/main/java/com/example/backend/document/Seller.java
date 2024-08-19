package com.example.backend.document;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "sellers")
public class Seller {
    @Id
    private String id;
    private String name;
    private String slug;
    private boolean uniqueIdentifier = false;
    @DBRef(lazy = true)
    private Image logo;
    private String address;
    private String description;
    private boolean status;
    @CreatedDate
    private LocalDateTime createdAt;
    @DBRef
    private User createBy;
}
