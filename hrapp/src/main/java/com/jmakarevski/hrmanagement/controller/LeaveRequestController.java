package com.jmakarevski.hrmanagement.controller;

import com.jmakarevski.hrmanagement.dto.LeaveRequestDto;
import com.jmakarevski.hrmanagement.dto.LeaveStatusUpdateDto;
import com.jmakarevski.hrmanagement.model.LeaveRequest;
import com.jmakarevski.hrmanagement.model.LeaveStatus;
import com.jmakarevski.hrmanagement.service.LeaveRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping
    public ResponseEntity<LeaveRequest> createLeaveRequest(@RequestBody LeaveRequestDto leaveRequestDto) {
        LeaveRequest leaveRequest = leaveRequestService.createLeaveRequest(leaveRequestDto);
        return ResponseEntity.ok(leaveRequest);
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequestDto>> getAllLeaveRequests() {
        List<LeaveRequestDto> leaveRequests = leaveRequestService.getAllLeaveRequests();
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("/status")
    public ResponseEntity<List<LeaveRequestDto>> getLeaveRequestsByStatus(@RequestParam LeaveStatus status) {
        List<LeaveRequestDto> leaveRequests = leaveRequestService.getLeaveRequestsByStatus(status);
        return ResponseEntity.ok(leaveRequests);
    }


    @GetMapping("/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByEmployee(@PathVariable Long employeeId) {
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByEmployee(employeeId);
        return ResponseEntity.ok(leaveRequests);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequestDto leaveRequestDto) {
        LeaveRequest leaveRequest = leaveRequestService.updateLeaveRequest(id, leaveRequestDto);
        return ResponseEntity.ok(leaveRequest);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<LeaveRequest> updateLeaveRequestStatus(@PathVariable Long id, @RequestBody LeaveStatusUpdateDto updateRequest) {
        LeaveRequest leaveRequest = leaveRequestService.updateLeaveRequestStatus(id, updateRequest.getStatus(), updateRequest.getReason());
        return ResponseEntity.ok(leaveRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
        return ResponseEntity.noContent().build();
    }
}
