package com.example.backend.service.Impl;

import com.example.backend.document.Image;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.image.ImageDto;
import com.example.backend.exception.error.NotFoundException;
import com.example.backend.repository.ImageRepository;
import com.example.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.Deflater;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    @Override
    public ImageDto uploadImageToMongoDB(MultipartFile file) throws IOException {
        Image imageData = imageRepository.save(
                Image.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .imageData(compressImage(file.getBytes()))
                        .build());
        return mapImageToImageDto(imageData);
    }

    @Override
    public ImageDto downloadImage(String id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hình ảnh với id : "+id));
        return mapImageToImageDto(image);
    }
    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }


    public ImageDto mapImageToImageDto(Image image){
        return ImageDto.builder()
                .id(image.getId())
                .name(image.getName())
                .type(image.getType())
                .imageData(image.getImageData())
                .build();
    }

    @Override
    public ApiObject deleteImage(String id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy hình ảnh với id : "+id));
        imageRepository.deleteById(id);
        return ApiObject.builder().message("Đã xóa hình ảnh thành công").build();
    }

    @Override
    public ImageDto updateImage(String id, MultipartFile file) throws IOException {
        Image image = imageRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy hình ảnh với id : "+id));
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImageData(compressImage(file.getBytes()));
        imageRepository.save(image);
        return mapImageToImageDto(image);
    }
}
