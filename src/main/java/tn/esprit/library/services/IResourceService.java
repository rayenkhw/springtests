package tn.esprit.library.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.library.entities.ImageModel;
import tn.esprit.library.entities.Resource;
import tn.esprit.library.entities.User;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IResourceService {
    public List<Resource> retrieveAllResources();

    public Resource retrieveResource(Long id_resource);

    public Resource addResource(Resource r);

    public void removeResource(Long id_resource);

    public Resource modifyResource(Resource r);

    /*
    public Resource affecterDocumentaResource(Resource r, Long id_document);

    public Resource affecterSubjectResource(Resource r, Long id_sujet);

     */
    Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException;

    Resource addResourceWithImages(Resource resource, MultipartFile[] files) throws IOException;

    User findUserById(Long userId); // Add this method

}