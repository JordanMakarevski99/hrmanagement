package com.jmakarevski.hrmanagement.service;

import com.jmakarevski.hrmanagement.dto.EmployeeDto;
import com.jmakarevski.hrmanagement.dto.LeaveRequestDto;
import com.jmakarevski.hrmanagement.model.LeaveRequest;
import com.jmakarevski.hrmanagement.model.LeaveStatus;
import com.jmakarevski.hrmanagement.repository.EmployeeRepository;
import com.jmakarevski.hrmanagement.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmakarevski.hrmanagement.exception.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.jmakarevski.hrmanagement.model.Employee;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public LeaveRequest createLeaveRequest(LeaveRequestDto leaveRequestDto) {
        List<LeaveRequest> activeRequests = leaveRequestRepository.findActiveRequestsByEmployeeId(leaveRequestDto.getEmployeeId());
        if (!activeRequests.isEmpty()) {
            throw new IllegalStateException("Employee has an active leave request.");
        }
        LeaveRequest leaveRequest = new LeaveRequest();
        Employee employee = employeeRepository.findById(leaveRequestDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        leaveRequest.setEmployee(employee);
        leaveRequest.setLeaveType(leaveRequestDto.getLeaveType());
        leaveRequest.setStartDate(leaveRequestDto.getStartDate());
        leaveRequest.setEndDate(leaveRequestDto.getEndDate());
        leaveRequest.setReason(leaveRequestDto.getReason());
        leaveRequest.setStatus(LeaveStatus.PENDING);
        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequestDto> getAllLeaveRequests() {
        List <LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        return leaveRequests.stream().map(request -> mapToDto(request)).collect(Collectors.toList());
    }
 
    public List<LeaveRequest> getLeaveRequestsByEmployee(Long employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId);
    }

    public LeaveRequest updateLeaveRequest(Long id, LeaveRequestDto leaveRequestDto) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LeaveRequest not found"));
        leaveRequest.setLeaveType(leaveRequestDto.getLeaveType());
        leaveRequest.setStartDate(leaveRequestDto.getStartDate());
        leaveRequest.setEndDate(leaveRequestDto.getEndDate());
        leaveRequest.setReason(leaveRequestDto.getReason());
        return leaveRequestRepository.save(leaveRequest);
    }
    public LeaveRequest updateLeaveRequestStatus(Long id, LeaveStatus status, String denialReason) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest not found"));

        leaveRequest.setStatus(status);
        if (status == LeaveStatus.APPROVED) {
            leaveRequest.setApprovalDate(LocalDate.now());
        } else if (status == LeaveStatus.REJECTED) {
            leaveRequest.setDenialDate(LocalDate.now());
            leaveRequest.setReason(denialReason);
        }

        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequestDto> getLeaveRequestsByStatus(LeaveStatus status) {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByStatus(status);
        return leaveRequests.stream()
            .map(request -> mapToDto(request))
            .collect(Collectors.toList());
    }
    private LeaveRequestDto mapToDto(LeaveRequest leaveRequest) {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(leaveRequest.getId());
        dto.setEmployee(mapToEmployeeDto(leaveRequest.getEmployee()));
        dto.setLeaveType(leaveRequest.getLeaveType());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setStatus(leaveRequest.getStatus());
        dto.setReason(leaveRequest.getReason());
        return dto;
    }

    private EmployeeDto mapToEmployeeDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        
        return dto;
    }
    public void deleteLeaveRequest(Long id) {
        leaveRequestRepository.deleteById(id);
    }
}
