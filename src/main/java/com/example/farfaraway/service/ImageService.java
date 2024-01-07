package com.example.farfaraway.service;

import com.example.farfaraway.entity.Image;
import com.example.farfaraway.entity.User;
import com.example.farfaraway.exception.NotFoundException;
import com.example.farfaraway.repository.ImageRepository;
import com.example.farfaraway.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public ImageService(ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    public List<Image> getFilesOfCurrentUser() {
        // TODO : Giả định userId = 1, sau này userId chính là user đang login
        Integer userId = 1;
        return imageRepository.findByUser_IdOrderByCreatedAtDesc(userId);
    }

    public Image uploadFile(MultipartFile file) throws IOException {
        // TODO : Giả định userId = 1, sau này userId chính là user đang login
        Integer userId = 1;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id " + userId));

        Image image = new Image();
        image.setType(file.getContentType());
        image.setData(file.getBytes());
        image.setUser(user);
        imageRepository.save(image);

        return image;
    }

    public Image getFileById(Integer id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found with id " + id));
    }

    public void deleteFile(Integer id) {
        Image file = getFileById(id);
        imageRepository.delete(file);
    }
}
