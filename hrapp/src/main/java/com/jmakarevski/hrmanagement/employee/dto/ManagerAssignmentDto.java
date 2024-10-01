package com.jmakarevski.hrmanagement.employee.dto;

public class ManagerAssignmentDto {
    private Long employeeId;
    private Long departmentId;

    // Default constructor
    public ManagerAssignmentDto() {}

    // Parameterized constructor
    public ManagerAssignmentDto(Long employeeId, Long departmentId) {
        this.employeeId = employeeId;
        this.departmentId = departmentId;
    }

    // Getters and setters
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
