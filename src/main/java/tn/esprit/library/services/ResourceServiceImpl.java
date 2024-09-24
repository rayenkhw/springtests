package tn.esprit.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.library.entities.ImageModel;
import tn.esprit.library.entities.Resource;
import tn.esprit.library.entities.User;
import tn.esprit.library.repository.IResourceRepository;
import tn.esprit.library.repository.IUserRepository;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    IResourceRepository resourceRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<Resource> retrieveAllResources() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource retrieveResource(Long id_resource) {
        return resourceRepository.findById(id_resource).orElse(null);
    }

    @Override
    public Resource addResource(Resource r) {
        return resourceRepository.save(r);
    }

    @Override
    public void removeResource(Long id_resource) {
        resourceRepository.deleteById(id_resource);
    }

    public Resource modifyResource(Resource r) {
        if (r.getId_resource() == null) {
            throw new IllegalArgumentException("Resource ID cannot be null");
        }
        return resourceRepository.save(r);
    }

    @Override
    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<>();
        for (MultipartFile file : multipartFiles) {
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            byte[] bytes = file.getBytes();
            ImageModel imageModel = new ImageModel(originalFilename, contentType, bytes);
            imageModels.add(imageModel);
        }
        return imageModels;
    }

    @Override
    public Resource addResourceWithImages(Resource resource, MultipartFile[] files) throws IOException {
        Set<ImageModel> images = uploadImage(files);
        resource.setResourceImages(images);
        return resourceRepository.save(resource);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}