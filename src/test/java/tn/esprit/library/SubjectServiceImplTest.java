package tn.esprit.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.library.entities.Subject;
import tn.esprit.library.repository.ISubjectRepository;
import tn.esprit.library.services.SubjectServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectServiceImplTest {

    @Mock
    private ISubjectRepository subjectRepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllSubjects() {
        // Arrange
        List<Subject> subjects = List.of(new Subject(), new Subject());
        when(subjectRepository.findAll()).thenReturn(subjects);

        // Act
        List<Subject> result = subjectService.retrieveAllSubjects();

        // Assert
        assertEquals(2, result.size());
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveSubject() {
        // Arrange
        Subject subject = new Subject();
        subject.setId_subject(1L);
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        // Act
        Subject result = subjectService.retrieveSubject(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId_subject());
        verify(subjectRepository, times(1)).findById(1L);
    }

    @Test
    void testAddSubject() {
        // Arrange
        Subject subject = new Subject();
        when(subjectRepository.save(subject)).thenReturn(subject);

        // Act
        Subject result = subjectService.addSubject(subject);

        // Assert
        assertNotNull(result);
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void testRemoveSubject() {
        // Arrange
        Long subjectId = 1L;
        doNothing().when(subjectRepository).deleteById(subjectId);

        // Act
        subjectService.removeSubject(subjectId);

        // Assert
        verify(subjectRepository, times(1)).deleteById(subjectId);
    }

    @Test
    void testModifySubject() {
        // Arrange
        Subject subject = new Subject();
        subject.setId_subject(1L);
        when(subjectRepository.save(subject)).thenReturn(subject);

        // Act
        Subject result = subjectService.modifySubject(subject);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId_subject());
        verify(subjectRepository, times(1)).save(subject);
    }
}

