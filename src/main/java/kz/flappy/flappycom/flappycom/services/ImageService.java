package kz.flappy.flappycom.flappycom.services;

import kz.flappy.flappycom.flappycom.entities.Images;

import java.util.List;

public interface ImageService {

    void deleteImage(Long id);
    List<Images> getAllImages();

    Images getImages(Long id);
    Images saveImages(Images images);
    Images addImages(Images images);

    List<Images> getAllImagesByUserId(Long id);

}
