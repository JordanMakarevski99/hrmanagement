package com.jmakarevski.hrmanagement.repository;

import com.jmakarevski.hrmanagement.model.LeaveRequest;
import com.jmakarevski.hrmanagement.model.LeaveStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
	@Query("SELECT lr FROM LeaveRequest lr WHERE lr.employee.id = :employeeId AND lr.status IN ('PENDING', 'APPROVED')")
	List<LeaveRequest> findActiveRequestsByEmployeeId(@Param("employeeId") Long employeeId);
    List<LeaveRequest> findByEmployeeId(Long employeeId);
   
    List<LeaveRequest> findByStatus(LeaveStatus status);

}
