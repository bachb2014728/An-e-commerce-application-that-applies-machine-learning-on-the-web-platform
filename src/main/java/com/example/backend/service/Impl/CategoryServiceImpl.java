package com.example.backend.service.Impl;

import com.example.backend.document.Category;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.category.*;
import com.example.backend.exception.error.AlreadyExistException;
import com.example.backend.exception.error.NotFoundException;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public List<CategoryItem> getAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new NotFoundException("Không tìm thấy danh mục nào.");
        }
        return categories.stream().map(this::convertCategoryItem).collect(Collectors.toList());
    }

    @Override
    public CategoryDto create(CategoryCreate categoryCreate) {
        if (categoryRepository.existsCategoryByName(categoryCreate.getName())){
            throw new AlreadyExistException("Đã tồn tại danh mục với tên : "+ categoryCreate.getName());
        }
        if (categoryRepository.existsCategoryBySlug(categoryCreate.getSlug())){
            throw new AlreadyExistException("Đã tồn tại danh mục với đường dẫn : "+categoryCreate.getSlug());
        }
        List<Category> categories = categoryRepository.findAll();

        Category category = Category.builder().name(categoryCreate.getName()).slug(categoryCreate.getSlug()).build();
        if (categories.stream().noneMatch(item -> Objects.equals(item.getId(), categoryCreate.getParentId())) || categoryCreate.getParentId() == null){
            category.setParentId("0");
        }else{
            Category categoryParent = categoryRepository.findById(categoryCreate.getParentId())
                    .orElseThrow(()-> new NotFoundException("Không tim thấy danh mục cha với parentId : "+ categoryCreate.getParentId()));
            if (!Objects.equals(categoryParent.getParentId(), "0")){
                category.setParentId("0");
            }else{
                category.setParentId(categoryCreate.getParentId());
            }
        }
        categoryRepository.save(category);

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .parentId(category.getParentId())
                .build();
    }

    @Override
    public CategoryDetail getOne(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy danh mục với id : "+id));
        return convertCategoryDetail(category);
    }

    @Override
    public CategoryDetail update(String id, CategoryUpdate categoryUpdate) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy danh mục với id : "+id));
        for (Category item : categoryRepository.findAll()){
            if (!item.getId().equals(category.getId()) && Objects.equals(item.getName(), categoryUpdate.getName())){
                throw new AlreadyExistException("Đã tồn tại danh mục với tên : "+ categoryUpdate.getName());
            }
            if (!item.getId().equals(category.getId()) && Objects.equals(item.getSlug(), categoryUpdate.getSlug())){
                throw new AlreadyExistException("Đã tồn tại danh mục với đường dẫn : "+ categoryUpdate.getSlug());
            }
        }

        category.setName(categoryUpdate.getName());
        category.setSlug(categoryUpdate.getSlug());

        if (categoryRepository.existsCategoryById(categoryUpdate.getParentId())){
            Category categoryParent = categoryRepository.findById(categoryUpdate.getParentId())
                    .orElseThrow(()-> new NotFoundException("Không tim thấy danh mục cha với parentId : "+ categoryUpdate.getParentId()));
            if (!Objects.equals(categoryParent.getParentId(), "0")){
                category.setParentId("0");
            }else{
                category.setParentId(categoryUpdate.getParentId());
            }
        }else{
            category.setParentId("0");
        }

        categoryRepository.save(category);
        return convertCategoryDetail(category);
    }

    @Override
    public ApiObject delete(String id) {
        return null;
    }

    @Override
    public List<CategoryDetail> getAllDepthOne() {
        List<Category> categoriesWithDepth0 = categoryRepository.findAllByParentId("0");
        if (categoriesWithDepth0.isEmpty()){
            throw new NotFoundException("Không tìm thấy danh mục nào cả.");
        }
        return categoriesWithDepth0.stream().map(category -> {
            List<Category> categoriesWithDepth1 = categoryRepository.findAllByParentId(category.getId());
            return CategoryDetail.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .slug(category.getSlug())
                    .parentId(category.getParentId())
                    .childes(categoriesWithDepth1.stream()
                            .map(this::convertCategoryItem)
                            .collect(Collectors.toList()))
                    .build();
        }).collect(Collectors.toList());
    }

    public CategoryItem convertCategoryItem(Category category){
        return CategoryItem.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .build();
    }
    public CategoryDetail convertCategoryDetail(Category category){
        return CategoryDetail.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .parentId(category.getParentId())
                .childes(categoryRepository.findAllByParentId(category.getId())
                        .stream().map(this::convertCategoryItem).collect(Collectors.toList()))
                .build();
    }
}
