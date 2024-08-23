package com.example.backend.service.Impl;

import com.example.backend.document.Color;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.color.ColorCreate;
import com.example.backend.dto.color.ColorUpdate;
import com.example.backend.exception.error.AlreadyExistException;
import com.example.backend.exception.error.NotFoundException;
import com.example.backend.repository.ColorRepository;
import com.example.backend.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public List<Color> getAll() {
        List<Color> colors = colorRepository.findAll();
        if (colors.isEmpty()){
            throw new NotFoundException("Không tìm thấy màu sắc nào!");
        }
        return colors;
    }

    @Override
    public Color create(ColorCreate colorCreate) {
        if (colorRepository.existsByNormalizedName(colorCreate.getName().toUpperCase())){
            throw new AlreadyExistException("Đã tồn tại màu sắc : "+colorCreate.getName());
        }
        return Color.builder()
                .name(colorCreate.getName())
                .status(true)
                .normalizedName(colorCreate.getName().toUpperCase())
                .build();
    }

    @Override
    public Color getOne(String id) {
        return colorRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy màu sắc với id : "+id));
    }

    @Override
    public Color update(String id, ColorUpdate colorUpdate) {
        Color color = colorRepository.findById(id).orElseThrow(()-> new NotFoundException("Không tìm thấy màu sắc với id : "+id));
        for (Color item : colorRepository.findAllByIdNotIn(Collections.singletonList(id))){
            if (colorUpdate.getName().toUpperCase().equals(item.getNormalizedName())){
                throw new AlreadyExistException("Đã tồn tại màu sắc : "+ colorUpdate.getName());
            }
        }
        color.setName(colorUpdate.getName());
        color.setNormalizedName(colorUpdate.getName().toUpperCase());
        color.setStatus(colorUpdate.isStatus());
        colorRepository.save(color);
        return color;
    }

    @Override
    public ApiObject delete(String id) {
        Color color = colorRepository.findById(id).orElseThrow(()->new NotFoundException("Không tìm thấy màu sắc với id : "+id));
        colorRepository.delete(color);
        return ApiObject.builder().message("Đã xóa thành công!").build();
    }
}
