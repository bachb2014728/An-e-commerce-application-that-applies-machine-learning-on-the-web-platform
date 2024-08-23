package com.example.backend.config;

import com.example.backend.document.Image;
import com.example.backend.repository.ImageRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class InitImageConfig {
    private final ImageRepository imageRepository;
    @Bean
    public CommandLineRunner initImages() {
        return args -> {
            List<Image> images = readImagesFromCsv();
            for (Image image : images){
                if (!imageRepository.existsByName(image.getName())){
                    imageRepository.save(image);
                }
            }
        };
    }

    private List<Image> readImagesFromCsv() {
        try (CSVReader reader = new CSVReader(
                new InputStreamReader(
                        new ClassPathResource("data/image.csv").getInputStream()
                ))
        ) {
            reader.skip(1);

            try {
                return reader.readAll().stream()
                        .map(line -> Image.builder()
                                .name(line[0])
                                .type(line[1])
                                .imageData(readImageFromClasspath(line[2]))
                                .build())
                        .collect(Collectors.toList());
            } catch (CsvException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file : ", e);
        }
    }
    private byte[] readImageFromClasspath(String path) {
        try (InputStream inputStream = new ClassPathResource(path).getInputStream()) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image from classpath: " + path, e);
        }
    }
}
