package edu.cit.eupena.felix.campusequipmentloan.repository;

import edu.cit.eupena.felix.campusequipmentloan.entity.Loan;
import edu.cit.eupena.felix.campusequipmentloan.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    long countByStudentIdAndStatus(Long studentId, LoanStatus status);
}
