package com.jmakarevski.hrmanagement.dto;

import java.time.LocalDate;

import com.jmakarevski.hrmanagement.model.Employee;

public class LeaveRequestResponseDto {

	private Long id;
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String reason;
    private LocalDate approvalDate;
    private LocalDate denialDate;

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public LocalDate getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(LocalDate approvalDate) {
		this.approvalDate = approvalDate;
	}
	public LocalDate getDenialDate() {
		return denialDate;
	}
	public void setDenialDate(LocalDate denialDate) {
		this.denialDate = denialDate;
	}
}