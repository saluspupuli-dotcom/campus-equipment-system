package edu.cit.eupena.felix.campusequipmentloan.strategy;

import edu.cit.eupena.felix.campusequipmentloan.entity.Loan;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Component
public class FixedPerDayPenalty implements PenaltyStrategy {
    private final BigDecimal perDay = BigDecimal.valueOf(50); // ₱50/day

    @Override
    public BigDecimal calculatePenalty(Loan loan) {
        if (loan.getReturnDate() == null || loan.getDueDate() == null) {
            return BigDecimal.ZERO;
        }
        long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
        if (daysLate <= 0) return BigDecimal.ZERO;
        return perDay.multiply(BigDecimal.valueOf(daysLate));
    }
}
