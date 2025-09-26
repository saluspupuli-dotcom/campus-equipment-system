package edu.cit.eupena.felix.campusequipmentloan.strategy;

import edu.cit.eupena.felix.campusequipmentloan.entity.Loan;
import java.math.BigDecimal;

public interface PenaltyStrategy {
    BigDecimal calculatePenalty(Loan loan);
}
