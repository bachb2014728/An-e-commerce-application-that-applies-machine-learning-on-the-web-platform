package com.example.backend.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String normalizedName;
    private String slug;
    private double price = 0;
    private int quantity = 0;
    private double weight = 0;
    private String description;
    @DBRef(lazy = true)
    private Category category;
    @DBRef(lazy = true)
    private Manufacturer manufacturer;
    @DBRef
    private List<Image> images;
    @DBRef(lazy = true)
    private List<Color> colors;
    @DBRef
    private List<Comment> comments;
    private double rating = 0;
    @DBRef(lazy = true)
    private Condition condition;
    private List<String> tags;
    private boolean status;
    @CreatedDate
    private LocalDateTime createdAt;
    @DBRef
    private Seller createdBy;
}
