	package com.jmakarevski.hrmanagement.controller;
	import com.jmakarevski.hrmanagement.dto.*;
	import com.jmakarevski.hrmanagement.model.*;
	import com.jmakarevski.hrmanagement.service.EmployeeService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	import java.util.List;
	import java.util.Optional;
import java.util.Set;
	
	@RestController
	@RequestMapping("/api/employees")
	public class EmployeeController {
	
	    @Autowired
	    private EmployeeService employeeService;
	
	    @GetMapping
	    public List<Employee> getAllEmployees() {
	        return employeeService.findAllEmployees();
	    }
	
	    @GetMapping("/{id}")
	    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
	        Optional<Employee> employee = employeeService.findEmployeeById(id);
	        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	    @GetMapping("/{id}/projects")
	    public ResponseEntity<Set<Project>> getEmployeeProjects(@PathVariable Long id) {
	        Optional<Employee> employee = employeeService.findEmployeeById(id);
	        if (employee.isPresent()) {
	            return ResponseEntity.ok(employee.get().getProjects());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @PostMapping
	    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDto employeeRequest) {
	        Employee employee = employeeService.createEmployee(
	                employeeRequest.getFirstName(),
	                employeeRequest.getLastName(),
	                employeeRequest.getEmail(),
	                employeeRequest.getHireDate(),
	                employeeRequest.getDepartment()
	        );
	        return ResponseEntity.ok(employee);
	    }
	    @PostMapping("/assign")
	    public ResponseEntity<Employee> assignEmployeeToDepartment(@RequestBody EmployeeAssignmentDto request) {
	        Employee employee = employeeService.assignEmployeeToDepartment(request.getEmployeeId(), request.getDepartmentId());
	        return ResponseEntity.ok(employee);
	    }
	
	    @PostMapping("/assign-manager")
	    public ResponseEntity<Department> assignManagerToDepartment(@RequestBody ManagerAssignmentDto request) {
	        Department department = employeeService.assignManagerToDepartment(request.getEmployeeId(), request.getDepartmentId());
	        return ResponseEntity.ok(department);
	    }
	
	    @PostMapping("/assign-project")
	    public ResponseEntity<Project> assignEmployeeToProject(@RequestBody ProjectAssignmentDto request) {
	        Project project = employeeService.assignEmployeeToProject(request.getEmployeeId(), request.getProjectId());
	        return ResponseEntity.ok(project);
	    }
	    
	    @PostMapping("/unassign-project")
	    public ResponseEntity<Project> unassignEmployeeFromProject(@RequestBody ProjectAssignmentDto request) {
	        Project project = employeeService.unassignEmployeeFromProject(request.getEmployeeId(), request.getProjectId());
	        return ResponseEntity.ok(project);
	    }
	    @PutMapping("/{id}")
	    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
	        if (!employeeService.findEmployeeById(id).isPresent()) {
	            return ResponseEntity.notFound().build();
	        }
	        employee.setId(id);
	        Employee updatedEmployee = employeeService.saveEmployee(employee);
	        return ResponseEntity.ok(updatedEmployee);
	    }
	   
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
	        if (!employeeService.findEmployeeById(id).isPresent()) {
	            return ResponseEntity.notFound().build();
	        }
	        employeeService.deleteEmployee(id);
	        return ResponseEntity.noContent().build();
	    }
	    @GetMapping("/{id}/department-name")
	    public ResponseEntity<String> getDepartmentNameByEmployeeId(@PathVariable Long id) {
	        String departmentName = employeeService.getDepartmentNameByEmployeeId(id);
	        return departmentName != null ? ResponseEntity.ok(departmentName) : ResponseEntity.notFound().build();
	    }
	}
