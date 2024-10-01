package com.jmakarevski.hrmanagement.controller;

import com.jmakarevski.hrmanagement.dto.EquipmentDto;
import com.jmakarevski.hrmanagement.model.Employee;
import com.jmakarevski.hrmanagement.model.Equipment;
import com.jmakarevski.hrmanagement.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @PostMapping
    public ResponseEntity<Equipment> createEquipment(@RequestBody EquipmentDto equipmentRequest) {
        Equipment equipment = equipmentService.createEquipment(equipmentRequest.getName(), equipmentRequest.getEmployeeId());
        return ResponseEntity.ok(equipment);
    }
    
    @PostMapping("/{equipmentId}/assign")
    public ResponseEntity<Equipment> assignEquipmentToEmployee(@PathVariable Long equipmentId, @RequestParam Long employeeId) {
        Equipment equipment = equipmentService.assignEquipmentToEmployee(equipmentId, employeeId);
        return ResponseEntity.ok(equipment);
    }
    @PostMapping("/{equipmentId}/unassign")
    public ResponseEntity<Equipment> unassignEquipmentFromEmployee(@PathVariable Long equipmentId) {
        Equipment equipment = equipmentService.unassignEquipmentFromEmployee(equipmentId);
        return ResponseEntity.ok(equipment);
    }

    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        return ResponseEntity.ok(equipmentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        Optional<Equipment> equipment = equipmentService.getEquipmentById(id);
        return equipment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
    @PutMapping("/{id}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable Long id, @RequestBody EquipmentDto equipmentRequest) {
        Equipment equipment = equipmentService.updateEquipment(id, equipmentRequest.getName(), equipmentRequest.getEmployeeId());
        if (equipment != null) {
            return ResponseEntity.ok(equipment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        boolean deleted = equipmentService.deleteEquipment(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
