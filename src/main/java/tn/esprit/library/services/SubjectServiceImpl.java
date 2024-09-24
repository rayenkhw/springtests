package tn.esprit.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.library.entities.Subject;
import tn.esprit.library.repository.ISubjectRepository;

import java.util.List;

@Service
public class SubjectServiceImpl implements ISubjectService {


    @Autowired
    ISubjectRepository subjectRepository;
    @Override
    public List<Subject> retrieveAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject retrieveSubject(Long id_subject) {
        return subjectRepository.findById(id_subject).get();
    }

    @Override
    public Subject addSubject(Subject s) {
        return subjectRepository.save(s);
    }

    @Override
    public void removeSubject(Long id_subject) {
        subjectRepository.deleteById(id_subject);

    }

    @Override
    public Subject modifySubject(Subject s) {
        return subjectRepository.save(s);
    }
}
