package tn.esprit.library.services;

import tn.esprit.library.entities.ImageModel;

public interface IImageService {
    ImageModel getImageById(Long id);
}
