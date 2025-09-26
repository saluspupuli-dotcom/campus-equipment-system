package edu.cit.eupena.felix.campusequipmentloan.repository;

import edu.cit.eupena.felix.campusequipmentloan.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
