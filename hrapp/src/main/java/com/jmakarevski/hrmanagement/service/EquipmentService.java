package com.jmakarevski.hrmanagement.service;

import com.jmakarevski.hrmanagement.model.Employee;
import com.jmakarevski.hrmanagement.repository.EmployeeRepository;
import com.jmakarevski.hrmanagement.model.Equipment;
import com.jmakarevski.hrmanagement.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public Optional<Equipment> getEquipmentById(Long id) {
        return equipmentRepository.findById(id);
    }

    public Equipment createEquipment(String name, Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            Equipment equipment = new Equipment(name, employee);
            return equipmentRepository.save(equipment);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    public Equipment updateEquipment(Long id, String name, Long employeeId) {
        Optional<Equipment> equipmentOpt = equipmentRepository.findById(id);
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);

        if (equipmentOpt.isPresent() && employeeOpt.isPresent()) {
            Equipment equipment = equipmentOpt.get();
            Employee employee = employeeOpt.get();
            equipment.setName(name);
            equipment.setEmployee(employee);
            return equipmentRepository.save(equipment);
        } else {
            throw new RuntimeException("Equipment or Employee not found");
        }
    }

    public boolean deleteEquipment(Long id) {
        if (equipmentRepository.existsById(id)) {
            equipmentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Equipment assignEquipmentToEmployee(Long equipmentId, Long employeeId) {
        Optional<Equipment> equipmentOpt = equipmentRepository.findById(equipmentId);
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);

        if (equipmentOpt.isPresent() && employeeOpt.isPresent()) {
            Equipment equipment = equipmentOpt.get();
            if (equipment.getEmployee() != null) {
                throw new RuntimeException("Equipment is already assigned to an employee");
            }

            Employee employee = employeeOpt.get();
            equipment.setEmployee(employee);
            return equipmentRepository.save(equipment);
        } else {
            throw new RuntimeException("Equipment or Employee not found");
        }
    }
    public Equipment unassignEquipmentFromEmployee(Long equipmentId) {
        Optional<Equipment> equipmentOpt = equipmentRepository.findById(equipmentId);

        if (equipmentOpt.isPresent()) {
            Equipment equipment = equipmentOpt.get();
            equipment.setEmployee(null);
            return equipmentRepository.save(equipment);
        } else {
            throw new RuntimeException("Equipment not found");
        }
    }
}
