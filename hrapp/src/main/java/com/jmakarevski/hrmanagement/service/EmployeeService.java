package com.jmakarevski.hrmanagement.service;

import com.jmakarevski.hrmanagement.model.Employee;
import com.jmakarevski.hrmanagement.model.Project;
import com.jmakarevski.hrmanagement.repository.EmployeeRepository;
import com.jmakarevski.hrmanagement.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmakarevski.hrmanagement.repository.*;
import com.jmakarevski.hrmanagement.model.Department;

import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Transactional
    public Employee createEmployee(String firstName, String lastName, String email, LocalDate hireDate, String departmentName) {
        Department department = departmentRepository.findByName(departmentName)
                .orElseGet(() -> departmentRepository.save(new Department(departmentName)));

        Employee employee = new Employee(firstName, lastName, email, hireDate, department);
        return employeeRepository.save(employee);
    }
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    @Transactional
    public Employee assignEmployeeToDepartment(Long employeeId, Long departmentId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId);

        if (employeeOpt.isPresent() && departmentOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            Department department = departmentOpt.get();
            employee.setDepartment(department);
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee or Department not found");
        }
    }
    @Transactional
    public Department assignManagerToDepartment(Long employeeId, Long departmentId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId);

        if (employeeOpt.isPresent() && departmentOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            Department department = departmentOpt.get();
            department.setManager(employee);
            return departmentRepository.save(department);
        } else {
            throw new RuntimeException("Employee or Department not found");
        }
    }
    @Transactional
    public Project assignEmployeeToProject(Long employeeId, Long projectId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        Optional<Project> projectOpt = projectRepository.findById(projectId);

        if (employeeOpt.isPresent() && projectOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            Project project = projectOpt.get();
            project.getEmployees().add(employee);
            return projectRepository.save(project);
        } else {
            throw new RuntimeException("Employee or Project not found");
        }
    }
    public Project unassignEmployeeFromProject(Long employeeId, Long projectId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (!employeeOpt.isPresent()) {
            throw new RuntimeException("Employee not found");
        }

        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (!projectOpt.isPresent()) {
            throw new RuntimeException("Project not found");
        }

        Employee employee = employeeOpt.get();
        Project project = projectOpt.get();

        project.getEmployees().remove(employee);

        return projectRepository.save(project);
    }


    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    public String getDepartmentNameByEmployeeId(Long employeeId) {
        return employeeRepository.findDepartmentNameByEmployeeId(employeeId);
    }
}
