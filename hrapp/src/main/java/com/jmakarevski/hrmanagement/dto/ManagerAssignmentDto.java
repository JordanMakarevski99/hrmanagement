package com.jmakarevski.hrmanagement.dto;

public class ManagerAssignmentDto {
    private Long employeeId;
    private Long departmentId;

    public ManagerAssignmentDto() {}

    public ManagerAssignmentDto(Long employeeId, Long departmentId) {
        this.employeeId = employeeId;
        this.departmentId = departmentId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
