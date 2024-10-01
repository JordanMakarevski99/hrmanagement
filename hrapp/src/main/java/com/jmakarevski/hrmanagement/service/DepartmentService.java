package com.jmakarevski.hrmanagement.service;

import com.jmakarevski.hrmanagement.dto.DepartmentDto;
import com.jmakarevski.hrmanagement.dto.EmployeeDto;
import com.jmakarevski.hrmanagement.model.Department;
import com.jmakarevski.hrmanagement.model.Employee;
import com.jmakarevski.hrmanagement.repository.DepartmentRepository;
import com.jmakarevski.hrmanagement.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Department createDepartment(Department department) {
        if (department.getManager() != null && department.getManager().getId() != null) {
            Employee manager = employeeRepository.findById(department.getManager().getId()).orElse(null);
            department.setManager(manager);
        }
        return departmentRepository.save(department);
    }

    @Transactional
    public Department updateDepartment(Long id, Department departmentRequest) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            department.setName(departmentRequest.getName());
            
            if (departmentRequest.getManager() != null && departmentRequest.getManager().getId() != null) {
                Employee manager = employeeRepository.findById(departmentRequest.getManager().getId()).orElse(null);
                department.setManager(manager);
            } else {
                department.setManager(null);
            }

            return departmentRepository.save(department);
        } else {
            throw new RuntimeException("Department not found");
        }
    }

    public List<DepartmentDto> findAllDepartments() {
        return departmentRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    public Optional<DepartmentDto> findDepartmentById(Long id) {
        return departmentRepository.findById(id)
            .map(this::convertToDto);
    }

    private DepartmentDto convertToDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setEmployeeCount(department.getEmployees().size());
        if (department.getManager() != null) {
            dto.setManagerId(department.getManager().getId());
        }
        return dto;
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
