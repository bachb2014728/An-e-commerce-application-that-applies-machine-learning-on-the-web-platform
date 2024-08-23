package com.example.backend.config;

import com.example.backend.document.Condition;
import com.example.backend.repository.ConditionRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class InitConditionConfig {
    private final ConditionRepository conditionRepository;
    @Bean
    public CommandLineRunner initConditions() {
        return args -> {
            List<Condition> conditions = readConditionsFromCsv();
            for (Condition condition : conditions){
                if (!conditionRepository.existsByNormalizedName(condition.getNormalizedName())){
                    conditionRepository.save(condition);
                }
            }
        };
    }

    private List<Condition> readConditionsFromCsv() {
        try (CSVReader reader = new CSVReader(
                new InputStreamReader(new ClassPathResource("data/condition.csv").getInputStream(), StandardCharsets.UTF_8))
        ) {
            // Bỏ qua dòng tiêu đề
            reader.skip(1);

            try {
                return reader.readAll().stream()
                        .map(line -> Condition.builder()
                                .name(line[0])
                                .normalizedName(line[0].toUpperCase())
                                .status(Boolean.parseBoolean(line[1]))
                                .build()
                        ).collect(Collectors.toList());
            } catch (CsvException e) {
                throw new RuntimeException("Error reading CSV file", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to open data file: conditions.csv", e);
        }
    }
}
