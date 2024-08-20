package com.example.backend.config;

import com.example.backend.data.AvatarImage;
import com.example.backend.document.Image;
import com.example.backend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.zip.Deflater;

@Configuration
@RequiredArgsConstructor
public class InitImageConfig {
    private final ImageRepository imageRepository;
    @Bean
    public CommandLineRunner initImages() {
        return args -> {
            if (!imageRepository.existsByName(AvatarImage.NAME)){
                Image image = Image.builder()
                        .id("i01")
                        .name(AvatarImage.NAME)
                        .type(AvatarImage.TYPE)
                        .imageData(Base64.getDecoder().decode(AvatarImage.IMAGE_DATA))
                        .build();
                imageRepository.save(image);
            }
        };
    }
}
