package com.example.backend.service;

import com.example.backend.document.Color;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.color.ColorCreate;
import com.example.backend.dto.color.ColorUpdate;

import java.util.List;

public interface ColorService {
    List<Color> getAll();

    Color create(ColorCreate colorCreate);

    Color getOne(String id);

    Color update(String id, ColorUpdate colorUpdate);

    ApiObject delete(String id);
}
