package edu.cit.eupena.felix.campusequipmentloan.repository;

import edu.cit.eupena.felix.campusequipmentloan.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByAvailabilityTrue();
}
