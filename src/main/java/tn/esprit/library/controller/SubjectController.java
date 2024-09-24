package tn.esprit.library.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.library.entities.Subject;
import tn.esprit.library.services.SubjectServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/subject")
@CrossOrigin(origins = "http://localhost:4200") // Allow specific origin
public class SubjectController {

    @Autowired
    SubjectServiceImpl subjectService;


    @PostMapping("/add")
    public  Subject addsubject(@RequestBody Subject s){return subjectService.addSubject(s) ;}

    @GetMapping("/getall")
    public List<Subject> getallsubjects(){return subjectService.retrieveAllSubjects();}

    @GetMapping("/getsubject/{ids}")
    public Subject getsubject(@PathVariable("ids")long id){return subjectService.retrieveSubject(id);}

    @PutMapping("/modif")
    public Subject mdoifysubject(@RequestBody Subject s){return subjectService.modifySubject(s);}

    @DeleteMapping("/del/{id}")
    public void deletesubject(@PathVariable("id")Long id){ subjectService.removeSubject(id);  }

}
