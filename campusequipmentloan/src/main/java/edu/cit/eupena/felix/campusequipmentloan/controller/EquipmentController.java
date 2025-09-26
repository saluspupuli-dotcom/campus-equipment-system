package edu.cit.eupena.felix.campusequipmentloan.controller;

import edu.cit.eupena.felix.campusequipmentloan.dto.EquipmentDTO;
import edu.cit.eupena.felix.campusequipmentloan.entity.Equipment;
import edu.cit.eupena.felix.campusequipmentloan.service.EquipmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping
    public EquipmentDTO create(@RequestBody EquipmentDTO dto) {
        Equipment equipment = new Equipment();
        equipment.setName(dto.name());
        equipment.setType(dto.type());
        equipment.setSerialNumber(dto.serialNumber());
        equipment.setAvailability(true);

        Equipment saved = equipmentService.save(equipment);

        return new EquipmentDTO(
                saved.getId(),
                saved.getName(),
                saved.getType(),
                saved.getSerialNumber()
        );
    }

    @GetMapping("/available")
    public List<EquipmentDTO> listAvailable() {
        return equipmentService.listAvailable()
                .stream()
                .map(e -> new EquipmentDTO(e.getId(), e.getName(), e.getType(), e.getSerialNumber()))
                .collect(Collectors.toList());
    }


    @GetMapping
    public List<EquipmentDTO> listAll() {
        return equipmentService.findAll()
                .stream()
                .map(e -> new EquipmentDTO(e.getId(), e.getName(), e.getType(), e.getSerialNumber()))
                .collect(Collectors.toList());
    }
}
