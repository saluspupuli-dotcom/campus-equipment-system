package edu.cit.eupena.felix.campusequipmentloan.service;

import edu.cit.eupena.felix.campusequipmentloan.entity.Student;
import edu.cit.eupena.felix.campusequipmentloan.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) { this.repo = repo; }

    public Student findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student save(Student s) { return repo.save(s); }
}
