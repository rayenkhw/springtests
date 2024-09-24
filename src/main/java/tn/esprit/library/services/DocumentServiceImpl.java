package tn.esprit.library.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.library.entities.ImageModel;
import tn.esprit.library.repository.IImageModelRepository;

import java.util.List;

@Service
public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    IImageModelRepository documentRepository;

    @Override
    public ImageModel saveDocument(ImageModel imageModel) {
        return documentRepository.save(imageModel);
    }

    @Override
    public List<ImageModel> saveDocuments(List<ImageModel> imageModels) {
        return documentRepository.saveAll(imageModels);
    }
}
