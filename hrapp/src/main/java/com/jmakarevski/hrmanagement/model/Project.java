package com.jmakarevski.hrmanagement.model;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
@Entity
@Table(name = "projects")
public class Project {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true, unique = true)
    private String description;
    
    @Column(nullable = true)
    LocalDate startDate;
    
    @Column(nullable = true)
    LocalDate endDate;
    
    @ManyToMany
    @JoinTable(
        name = "employee_project",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @JsonManagedReference
    private Set<Employee> employees;

	@ManyToOne
    @JoinColumn(name = "team_leader_id")
    private Employee teamLeader;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;
    
    
    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = ProjectStatus.STARTED;
        }
    }
    public Project() {}

    public Project(String name, String description, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    
    public Employee getTeamLeader() {
 		return teamLeader;
 	}

 	public void setTeamLeader(Employee teamLeader) {
 		this.teamLeader = teamLeader;
 	}

 	public ProjectStatus getStatus() {
 		return status;
 	}

 	public void setStatus(ProjectStatus status) {
 		this.status = status;
 	}

}
