package com.example.backend.service.Impl;

import com.example.backend.data.AvatarImage;
import com.example.backend.document.*;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.category.CategoryItem;
import com.example.backend.dto.manufacturer.ManufacturerItem;
import com.example.backend.dto.product.ProductCreate;
import com.example.backend.dto.product.ProductDto;
import com.example.backend.dto.product.ProductItem;
import com.example.backend.dto.product.ProductUpdate;
import com.example.backend.exception.error.NotFoundException;
import com.example.backend.repository.*;
import com.example.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ImageRepository imageRepository;
    private final SellerRepository sellerRepository;

    @Override
    public List<ProductItem> getAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()){
            throw new NotFoundException("Không tìm thấy sản phẩm nào.");
        }
        return products.stream().map(this::convertProductItem).collect(Collectors.toList());
    }

    @Override
    public ProductDto create(ProductCreate productCreate) {
        Category category = categoryRepository.findById(productCreate.getCategoryId()).orElse(null);
        Manufacturer manufacturer = manufacturerRepository.findById(productCreate.getManufacturerId()).orElse(null);
        List<Image> images = imageRepository.findByIdIn(productCreate.getImages());
        Seller seller = sellerRepository.findById(productCreate.getSellerId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cửa hàng đã thêm sản phẩm với id : "+productCreate.getSellerId()));
        Product product = Product.builder()
                .name(productCreate.getName())
                .slug(productCreate.getSlug())
                .price(productCreate.getPrice())
                .quantity(productCreate.getQuantity())
                .category(category)
                .manufacturer(manufacturer)
                .images(images)
                .status(true)
                .size(productCreate.getSize())
                .color(productCreate.getColor())
                .condition(productCreate.getCondition())
                .weight(productCreate.getWeight())
                .tags(productCreate.getTags())
                .material(productCreate.getMaterial())
                .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime())
                .createdBy(seller)
                .build();
        Product productNew = productRepository.save(product);
        return convertProductDto(productNew);
    }

    @Override
    public ProductDto getOne(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Không tìm thấy sản phẩm với id : "+id));
        return convertProductDto(product);
    }

    @Override
    public ProductDto update(String id, ProductUpdate productUpdate) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy sản phẩm với id : "+id));
        product.setName(productUpdate.getName());
        product.setSlug(productUpdate.getSlug());
        product.setColor(productUpdate.getColor());
        product.setSize(productUpdate.getSize());
        product.setMaterial(productUpdate.getMaterial());
        product.setWeight(productUpdate.getWeight());
        product.setTags(productUpdate.getTags());
        product.setCondition(productUpdate.getCondition());
        Manufacturer manufacturer = manufacturerRepository.findById(productUpdate.getManufacturerId()).orElse(product.getManufacturer());
        Category category = categoryRepository.findById(productUpdate.getCategoryId()).orElse(product.getCategory());
        product.setCategory(category);
        product.setManufacturer(manufacturer);
        List<Image> images = imageRepository.findByIdIn(productUpdate.getImages());
        for (Image image : product.getImages()){
            if (!isProductInList(images, image)){
                if (!imageRepository.existsByName(AvatarImage.NAME)){
                    imageRepository.deleteById(image.getId());
                }
            }
        }
        product.setImages(images);
        productRepository.save(product);
        return convertProductDto(product);
    }
    public boolean isProductInList(List<Image> images, Image imageToCheck) {
        return images.contains(imageToCheck);
    }

    @Override
    public ApiObject delete(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Không tìm thấy sản phẩm với id : "+id));
        product.setStatus(false);
        return ApiObject.builder().type("DELETE").message("Tạm xóa sản phẩm khỏi cửa hàng!").build();
    }
    public ProductItem convertProductItem(Product product){
        System.out.println(product.getImages());
        return ProductItem.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .tags(product.getTags())
                .sellerId(product.getCreatedBy().getId())
                .build();
    }
    public ProductDto convertProductDto(Product product){
        ManufacturerItem manufacturerItem = ManufacturerItem.builder()
                .id(product.getManufacturer().getId())
                .name(product.getManufacturer().getName())
                .slug(product.getManufacturer().getSlug())
                .build();
        CategoryItem categoryItem = CategoryItem.builder()
                .id(product.getCategory().getId())
                .name(product.getCategory().getName())
                .slug(product.getCategory().getSlug())
                .build();
        List<String> images = product.getImages().stream().map(Image::getId).toList();
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .manufacturer(manufacturerItem)
                .category(categoryItem)
                .images(images)
                .size(product.getSize())
                .status(product.isStatus())
                .color(product.getColor())
                .condition(product.getCondition())
                .weight(product.getWeight())
                .material(product.getMaterial())
                .tags(product.getTags())
                .createdAt(product.getCreatedAt())
                .sellerId(product.getCreatedBy().getId())
                .build();
    }
}
