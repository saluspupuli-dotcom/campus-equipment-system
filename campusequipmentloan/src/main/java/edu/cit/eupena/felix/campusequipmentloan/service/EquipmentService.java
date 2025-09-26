package edu.cit.eupena.felix.campusequipmentloan.service;

import edu.cit.eupena.felix.campusequipmentloan.entity.Equipment;
import edu.cit.eupena.felix.campusequipmentloan.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<Equipment> listAvailable() {
        return equipmentRepository.findByAvailabilityTrue();
    }

    public Equipment findById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    // ✅ Add this method
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }
}
