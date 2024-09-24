package tn.esprit.library.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.library.entities.ImageModel;
import tn.esprit.library.repository.ImageRepository;
import tn.esprit.library.services.IImageService;

@Service
public class ImageServiceImpl implements IImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ImageModel getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
}
