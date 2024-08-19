package com.example.backend.service.Impl;

import com.example.backend.document.Image;
import com.example.backend.document.Seller;
import com.example.backend.document.User;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.seller.SellerCreate;
import com.example.backend.dto.seller.SellerDto;
import com.example.backend.dto.seller.SellerItem;
import com.example.backend.dto.seller.SellerUpdate;
import com.example.backend.dto.user.UserDto;
import com.example.backend.exception.error.AlreadyExistException;
import com.example.backend.exception.error.NotFoundException;
import com.example.backend.repository.ImageRepository;
import com.example.backend.repository.SellerRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Override
    public List<SellerItem> getAll() {
        List<Seller> sellers = sellerRepository.findAll();
        if (sellers.isEmpty()){
            throw new NotFoundException("Không tìm thấy danh sách cửa hàng.");
        }
        return sellers.stream().map(this::convertSellerToSellerItem).collect(Collectors.toList());
    }

    @Override
    public SellerDto create(SellerCreate sellerCreate) {
        for (Seller item : sellerRepository.findAllByUniqueIdentifier(true)){
            if (sellerCreate.getName().equals(item.getName())){
                throw new AlreadyExistException("Cửa hàng đã tồn tại với tên : "+sellerCreate.getName());
            }
            if (sellerCreate.getSlug().equals(item.getSlug())){
                throw new AlreadyExistException("Cửa hàng đã tồn tại với đường dẫn : "+sellerCreate.getSlug());
            }
        }
        Image image = imageRepository.findById("i01").get();
        User user = userRepository.findById(sellerCreate.getUserId())
                .orElseThrow(()-> new NotFoundException("Không tìm thấy thông tin người dùng với id : "+sellerCreate.getUserId()));
        Seller seller = Seller.builder()
                .name(sellerCreate.getName())
                .slug(sellerCreate.getSlug())
                .status(true)
                .address(sellerCreate.getAddress())
                .description(sellerCreate.getDescription())
                .uniqueIdentifier(false)
                .logo(image)
                .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime())
                .createBy(user)
                .build();
        sellerRepository.save(seller);
        return convertSellerDto(seller);
    }

    @Override
    public SellerDto getOne(String id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Không tìm thấy cửa hàng với id : "+id));
        if (!seller.isStatus()){
            throw new NotFoundException("Cửa hàng dã bị xóa với id : "+id);
        }
        return convertSellerDto(seller);
    }

    @Override
    public SellerDto update(String id, SellerUpdate sellerUpdate) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cửa hàng với id : "+id));
        List<Seller> sellers = sellerRepository
                .findAllByUniqueIdentifierAndIdNotIn(true, Collections.singletonList(seller.getId()));
        for (Seller item : sellers){
            if (item.getName().equals(sellerUpdate.getName())){
                throw new AlreadyExistException("Đã tồn tại cửa hàng đã đăng kí tên bản quyền với tên : "+sellerUpdate.getName());
            }
            if (item.getSlug().equals(sellerUpdate.getSlug())){
                throw new AlreadyExistException("Đã tồn tại cửa hàng với đường dẫn đã đăng kí bản quyền với : "+sellerUpdate.getSlug());
            }
        }
        if (!sellerUpdate.getImageId().isEmpty()){
            Image image = imageRepository.findById(sellerUpdate.getImageId()).orElse(imageRepository.findById("i01").get());
            imageRepository.delete(seller.getLogo());
            seller.setLogo(image);
        }
        seller.setName(sellerUpdate.getName());
        seller.setSlug(sellerUpdate.getSlug());
        sellerRepository.save(seller);

        return convertSellerDto(seller);
    }

    @Override
    public ApiObject delete(String id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cửa hàng với id : "+id));
        seller.setStatus(false);
        sellerRepository.save(seller);
        return ApiObject.builder().type("DELETE").message("Đã xóa cửa hàng thành công.").build();
    }
    public SellerItem convertSellerToSellerItem(Seller seller){
        return SellerItem.builder()
                .id(seller.getId())
                .name(seller.getName())
                .status(seller.isStatus())
                .slug(seller.getSlug())
                .uniqueIdentifier(seller.isUniqueIdentifier())
                .build();
    }
    public UserDto convertUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accountId(user.getAccount().getId())
                .build();
    }
    public SellerDto convertSellerDto(Seller seller){
        return SellerDto.builder()
                .id(seller.getId())
                .name(seller.getName())
                .slug(seller.getSlug())
                .imageId(seller.getLogo().getId())
                .uniqueIdentifier(seller.isUniqueIdentifier())
                .address(seller.getAddress())
                .status(seller.isStatus())
                .createdAt(seller.getCreatedAt())
                .description(seller.getDescription())
                .createBy(convertUserDto(seller.getCreateBy()))
                .build();
    }
}
