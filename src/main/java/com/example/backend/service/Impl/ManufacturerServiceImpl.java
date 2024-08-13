package com.example.backend.service.Impl;

import com.example.backend.document.Manufacturer;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.manufacturer.ManufacturerCreate;
import com.example.backend.dto.manufacturer.ManufacturerDetail;
import com.example.backend.dto.manufacturer.ManufacturerItem;
import com.example.backend.dto.manufacturer.ManufacturerUpdate;
import com.example.backend.exception.error.AlreadyExistException;
import com.example.backend.exception.error.NotFoundException;
import com.example.backend.repository.ManufacturerRepository;
import com.example.backend.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    @Override
    public List<ManufacturerItem> getAll() {
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        if (manufacturers.isEmpty()){
            throw  new NotFoundException("Không tìm thấy nhà sản xuất nào.");
        }
        return manufacturers.stream().map(this::convertManufacturerItem).collect(Collectors.toList());
    }

    @Override
    public ManufacturerDetail getOne(String id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy nhà sản xuất với id : "+id));
        return ManufacturerDetail.builder()
                .id(manufacturer.getId())
                .name(manufacturer.getName())
                .slug(manufacturer.getSlug())
                .build();
    }

    @Override
    public ManufacturerItem create(ManufacturerCreate manufacturerCreate) {
        if (manufacturerRepository.existsManufacturerByName(manufacturerCreate.getName())){
            throw new AlreadyExistException("Đã tồn tại nhà sản xuất với tên :" + manufacturerCreate.getName());
        }
        if (manufacturerRepository.existsManufacturerBySlug(manufacturerCreate.getSlug())){
            throw new AlreadyExistException("Đã tồn tại nhà sản xuất với đường dẫn : "+ manufacturerCreate.getSlug());
        }
        Manufacturer manufacturer = Manufacturer.builder()
                .name(manufacturerCreate.getName())
                .slug(manufacturerCreate.getSlug())
                .build();
        manufacturerRepository.save(manufacturer);
        return convertManufacturerItem(manufacturer);
    }

    @Override
    public ManufacturerDetail update(ManufacturerUpdate manufacturerUpdate, String id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy nhà sản xuất với id : "+id));

        List<String> ids = Collections.singletonList(manufacturer.getId());

        for (Manufacturer item : manufacturerRepository.findAllByIdNotIn(ids)){
            if (item.getName().equals(manufacturerUpdate.getName())){
                throw new AlreadyExistException("Đã tồn tại nhà sản xuất với tên : "+ manufacturerUpdate.getName());
            }
            if (item.getSlug().equals(manufacturerUpdate.getSlug())){
                throw new AlreadyExistException("Đã tồn tại nhà sản xuất với đường dẫn : "+ manufacturerUpdate.getSlug());
            }
        }

        manufacturer.setName(manufacturerUpdate.getName());
        manufacturer.setSlug(manufacturerUpdate.getSlug());

        Manufacturer result = manufacturerRepository.save(manufacturer);
        return ManufacturerDetail.builder()
                .id(result.getId())
                .name(result.getName())
                .slug(result.getSlug())
                .build();
    }

    @Override
    public ApiObject delete(String id) {
        return null;
    }
    public ManufacturerItem convertManufacturerItem(Manufacturer manufacturer){
        return ManufacturerItem.builder()
                .id(manufacturer.getId())
                .name(manufacturer.getName())
                .slug(manufacturer.getSlug())
                .build();
    }
}
