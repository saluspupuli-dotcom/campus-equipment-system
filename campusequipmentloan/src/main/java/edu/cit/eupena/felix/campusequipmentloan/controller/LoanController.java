package edu.cit.eupena.felix.campusequipmentloan.controller;

import edu.cit.eupena.felix.campusequipmentloan.dto.CreateLoanRequest;
import edu.cit.eupena.felix.campusequipmentloan.dto.LoanResponseDTO;
import edu.cit.eupena.felix.campusequipmentloan.entity.Loan;
import edu.cit.eupena.felix.campusequipmentloan.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) { this.loanService = loanService; }

    @PostMapping
    public LoanResponseDTO createLoan(@Valid @RequestBody CreateLoanRequest req) {
        Loan loan = loanService.createLoan(req);
        return toDto(loan);
    }

    @PostMapping("/{id}/return")
    public LoanResponseDTO returnLoan(@PathVariable Long id) {
        Loan loan = loanService.returnLoan(id);
        return toDto(loan);
    }

    private LoanResponseDTO toDto(Loan loan) {
        LoanResponseDTO dto = new LoanResponseDTO();
        dto.setId(loan.getId());
        dto.setEquipmentId(loan.getEquipment().getId());
        dto.setStudentId(loan.getStudent().getId());
        dto.setStartDate(loan.getStartDate());
        dto.setDueDate(loan.getDueDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setStatus(loan.getStatus().name());
        dto.setPenalty(loan.getPenalty());
        return dto;
    }
}
