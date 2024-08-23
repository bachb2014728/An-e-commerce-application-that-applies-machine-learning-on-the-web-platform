package com.example.backend.controller;

import com.example.backend.document.Color;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.color.ColorCreate;
import com.example.backend.dto.color.ColorUpdate;
import com.example.backend.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/colors")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;
    @GetMapping("")
    public ResponseEntity<List<Color>> getAll(){
        return ResponseEntity.ok(colorService.getAll());
    }
    @PostMapping("")
    public ResponseEntity<Color> create(@RequestBody ColorCreate colorCreate){
        return ResponseEntity.ok(colorService.create(colorCreate));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Color> getOne(@PathVariable String id){
        return ResponseEntity.ok(colorService.getOne(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Color> update(@PathVariable String id, @RequestBody ColorUpdate colorUpdate){
        return ResponseEntity.ok(colorService.update(id,colorUpdate));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiObject> delete(@PathVariable String id){
        return ResponseEntity.ok(colorService.delete(id));
    }
}
