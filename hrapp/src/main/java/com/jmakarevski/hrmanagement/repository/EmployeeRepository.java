package com.jmakarevski.hrmanagement.repository;

import com.jmakarevski.hrmanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	@Query("SELECT e.department.name FROM Employee e WHERE e.id = :employeeId")
	String findDepartmentNameByEmployeeId(Long employeeId);
}
