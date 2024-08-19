package com.example.backend.controller;

import com.example.backend.dto.ApiObject;
import com.example.backend.dto.image.ImageDto;
import com.example.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("")
    public ResponseEntity<ImageDto> uploadImageToMongoDB(@RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(imageService.uploadImageToMongoDB(file));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ImageDto> downloadImage(@PathVariable String id){
        return ResponseEntity.ok(imageService.downloadImage(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ImageDto> updateImage(
            @PathVariable String id, @RequestParam("image") MultipartFile file) throws IOException{
        return ResponseEntity.ok(imageService.updateImage(id,file));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiObject> deleteImage(@PathVariable String id){
        return ResponseEntity.ok(imageService.deleteImage(id));
    }
}
