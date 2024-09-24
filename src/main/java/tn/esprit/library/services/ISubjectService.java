package tn.esprit.library.services;

import tn.esprit.library.entities.Subject;

import java.util.List;

public interface ISubjectService {
    public List<Subject> retrieveAllSubjects();

    public Subject retrieveSubject(Long id_subject);

    public Subject addSubject(Subject s);

    public void removeSubject(Long id_subject);

    public Subject modifySubject(Subject s);


}
