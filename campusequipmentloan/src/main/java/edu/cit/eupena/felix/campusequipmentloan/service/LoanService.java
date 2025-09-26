package edu.cit.eupena.felix.campusequipmentloan.service;

import edu.cit.eupena.felix.campusequipmentloan.dto.CreateLoanRequest;
import edu.cit.eupena.felix.campusequipmentloan.entity.*;
import edu.cit.eupena.felix.campusequipmentloan.repository.LoanRepository;
import edu.cit.eupena.felix.campusequipmentloan.strategy.PenaltyStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final EquipmentService equipmentService;
    private final StudentService studentService;
    private final PenaltyStrategy penaltyStrategy;

    private static final int MAX_ACTIVE = 2;
    private static final int LOAN_LENGTH_DAYS = 7;

    public LoanService(LoanRepository loanRepository,
                       EquipmentService equipmentService,
                       StudentService studentService,
                       PenaltyStrategy penaltyStrategy) {
        this.loanRepository = loanRepository;
        this.equipmentService = equipmentService;
        this.studentService = studentService;
        this.penaltyStrategy = penaltyStrategy;
    }

    @Transactional
    public Loan createLoan(CreateLoanRequest req) {
        Equipment equipment = equipmentService.findById(req.getEquipmentId());
        if (!equipment.isAvailability()) {
            throw new RuntimeException("Equipment is not available");
        }

        Student student = studentService.findById(req.getStudentId());

        long active = loanRepository.countByStudentIdAndStatus(student.getId(), LoanStatus.ACTIVE);
        if (active >= MAX_ACTIVE) {
            throw new RuntimeException("Student already has maximum number of active loans");
        }

        LocalDate start = LocalDate.now();
        LocalDate due = start.plusDays(LOAN_LENGTH_DAYS);

        Loan loan = new Loan();
        loan.setEquipment(equipment);
        loan.setStudent(student);
        loan.setStartDate(start);
        loan.setDueDate(due);
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setPenalty(BigDecimal.ZERO);

        equipment.setAvailability(false);
        equipmentService.save(equipment);

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new RuntimeException("Loan is already returned");
        }

        loan.setReturnDate(LocalDate.now());
        BigDecimal penalty = penaltyStrategy.calculatePenalty(loan);
        loan.setPenalty(penalty);

        // Mark status: returned (if penalty > 0 you might still set OVERDUE but we use RETURNED + penalty)
        loan.setStatus(LoanStatus.RETURNED);

        // free equipment
        Equipment equipment = loan.getEquipment();
        equipment.setAvailability(true);
        equipmentService.save(equipment);

        return loanRepository.save(loan);
    }
}
