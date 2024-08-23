package com.example.backend.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "conditions")
//tình trạng : mới ,đã qua sử dung, đã sửa chữa , like-new
public class Condition {
    @Id
    private String id;
    private String name;
    private String normalizedName;
    private boolean status;
}
