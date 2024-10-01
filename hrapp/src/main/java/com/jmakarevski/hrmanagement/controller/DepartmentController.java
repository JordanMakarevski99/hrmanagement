	package com.jmakarevski.hrmanagement.controller;
	
	import com.jmakarevski.hrmanagement.dto.DepartmentDto;
	import com.jmakarevski.hrmanagement.model.Department;
	import com.jmakarevski.hrmanagement.model.Employee;
	import com.jmakarevski.hrmanagement.service.DepartmentService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	
	import java.util.List;
	import java.util.Optional;
	
	@RestController
	@RequestMapping("/api/departments")
	public class DepartmentController {
	
	    @Autowired
	    private DepartmentService departmentService;
	
	    @GetMapping
	    public List<DepartmentDto> getAllDepartments() {
	        return departmentService.findAllDepartments();
	    }
	
	    @GetMapping("/{id}")
	    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
	        Optional<DepartmentDto> department = departmentService.findDepartmentById(id);
	        return department.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	
	    @PostMapping
	    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentDto departmentRequest) {
	        Department department = new Department(departmentRequest.getName());
	        System.out.println(departmentRequest.getManagerId());
	        if (departmentRequest.getManagerId() != null) {
	            Employee manager = new Employee();
	            manager.setId(departmentRequest.getManagerId());
	            department.setManager(manager);
	        }
	        Department savedDepartment = departmentService.createDepartment(department);
	        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
	    }
	
	
	    @PutMapping("/{id}")
	    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department departmentRequest) {
	        try {
	            Department updatedDepartment = departmentService.updateDepartment(id, departmentRequest);
	            return ResponseEntity.ok(updatedDepartment);
	        } catch (RuntimeException e) {
	            return ResponseEntity.notFound().build();
	        }
	    }

	
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
	        if (!departmentService.findDepartmentById(id).isPresent()) {
	            return ResponseEntity.notFound().build();
	        }
	        departmentService.deleteDepartment(id);
	        return ResponseEntity.noContent().build();
	    }
	}
