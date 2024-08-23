package com.example.backend.service.Impl;

import com.example.backend.document.Condition;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.condition.ConditionCreate;
import com.example.backend.dto.condition.ConditionUpdate;
import com.example.backend.exception.error.AlreadyExistException;
import com.example.backend.exception.error.NotFoundException;
import com.example.backend.repository.ConditionRepository;
import com.example.backend.service.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionServiceImpl implements ConditionService {
    private final ConditionRepository conditionRepository;

    @Override
    public List<Condition> getAll() {
        List<Condition> conditions = conditionRepository.findAll();
        if (conditions.isEmpty()){
            throw new NotFoundException("Không tìm thấy dữ liệu nào!");
        }
        return conditions;
    }

    @Override
    public Condition create(ConditionCreate create) {
        if (conditionRepository.existsByNormalizedName(create.getName().toUpperCase())){
            throw new AlreadyExistException("Đã tồn tại tình trạng với tên : "+create.getName());
        }
        return Condition.builder()
                .name(create.getName())
                .normalizedName(create.getName().toUpperCase())
                .status(true)
                .build();
    }

    @Override
    public Condition getOne(String id) {
        return conditionRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Không tìm thấy tình trạng sản phẩm với id : "+id));
    }

    @Override
    public Condition update(String id, ConditionUpdate update) {
        Condition condition = conditionRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Không tìm thấy tình trạng của sản phẩm với id : "+id));
        for (Condition item : conditionRepository.findAllByIdNotIn(Collections.singletonList(id))){
            if (item.getNormalizedName().equals(update.getName().toUpperCase())){
                throw new AlreadyExistException("Đã tồn tai trạng thái với tên : "+update.getName());
            }
        }
        condition.setName(update.getName());
        condition.setNormalizedName(update.getName().toUpperCase());
        condition.setStatus(update.isStatus());
        conditionRepository.save(condition);
        return condition;
    }

    @Override
    public ApiObject delete(String id) {
        Condition condition = conditionRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy tình trạng với id : "+id));
        condition.setStatus(false);
        conditionRepository.save(condition);
        return ApiObject.builder().message("Đã ẩn dữ liệu thành công!").build();
    }
}
