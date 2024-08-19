package com.example.backend.service;

import com.example.backend.dto.ApiObject;
import com.example.backend.dto.image.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ImageDto uploadImageToMongoDB(MultipartFile file) throws IOException;

    ImageDto downloadImage(String fileName);

    ApiObject deleteImage(String id);

    ImageDto updateImage(String id, MultipartFile file) throws IOException;
}
