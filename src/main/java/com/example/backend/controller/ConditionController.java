package com.example.backend.controller;

import com.example.backend.document.Condition;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.condition.ConditionCreate;
import com.example.backend.dto.condition.ConditionUpdate;
import com.example.backend.service.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/conditions")
public class ConditionController {
    private final ConditionService conditionService;
    @GetMapping("")
    public ResponseEntity<List<Condition>> getAllCondition(){
        return ResponseEntity.ok(conditionService.getAll());
    }
    @PostMapping("")
    public ResponseEntity<Condition> create(@RequestBody ConditionCreate create){
        return ResponseEntity.ok(conditionService.create(create));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Condition> getOne(@PathVariable String id){
        return ResponseEntity.ok(conditionService.getOne(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Condition> update(@PathVariable String id, @RequestBody ConditionUpdate update){
        return ResponseEntity.ok(conditionService.update(id,update));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiObject> delete(@PathVariable String id){
        return ResponseEntity.ok(conditionService.delete(id));
    }
}
