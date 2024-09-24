package tn.esprit.library.services;

import tn.esprit.library.entities.ImageModel;

import java.util.List;

public interface IDocumentService {
    ImageModel saveDocument(ImageModel imageModel);
    List<ImageModel> saveDocuments(List<ImageModel> imageModels);

}
