package com.jmakarevski.hrmanagement.dto;

import com.jmakarevski.hrmanagement.model.LeaveStatus;

public class LeaveStatusUpdateDto {
    private LeaveStatus status;
    private String reason;
    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
