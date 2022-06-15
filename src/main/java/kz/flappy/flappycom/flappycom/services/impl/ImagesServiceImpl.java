package kz.flappy.flappycom.flappycom.services.impl;

import kz.flappy.flappycom.flappycom.entities.Images;
import kz.flappy.flappycom.flappycom.repositories.ImageRepository;
import kz.flappy.flappycom.flappycom.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public List<Images> getAllImages() {
        return imageRepository.findAllByAddedDateIsNotNullOrderByAddedDateDesc();
    }

    @Override
    public List<Images> getAllImagesByUserId(Long id) {
        return imageRepository.findAllByUsersId(id);
    }

    @Override
    public Images getImages(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public Images saveImages(Images images) {
        return imageRepository.save(images);
    }

    @Override
    public Images addImages(Images images) {
        return imageRepository.save(images);
    }
}
