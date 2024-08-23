package com.example.backend.service;

import com.example.backend.document.Condition;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.condition.ConditionCreate;
import com.example.backend.dto.condition.ConditionUpdate;

import java.util.List;

public interface ConditionService {
    List<Condition> getAll();

    Condition create(ConditionCreate create);

    Condition getOne(String id);

    Condition update(String id, ConditionUpdate update);

    ApiObject delete(String id);
}
