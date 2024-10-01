package com.jmakarevski.hrmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmakarevski.hrmanagement.model.Department;
import com.jmakarevski.hrmanagement.model.Employee;
import com.jmakarevski.hrmanagement.model.Project;
import com.jmakarevski.hrmanagement.model.ProjectStatus;
import com.jmakarevski.hrmanagement.repository.EmployeeRepository;
import com.jmakarevski.hrmanagement.repository.ProjectRepository;

@Service
public class ProjectService {
	  	@Autowired
	    private ProjectRepository projectRepository;
	    
	  	@Autowired
	  	private EmployeeRepository employeeRepository;
	  	
	    @Transactional
	    public Project createProject(String name, String description, LocalDate startDate, LocalDate endDate) {

	        Project project = new Project(name, description, startDate, endDate);
	        return projectRepository.save(project);
	    }
	    public List<Project> findAllProjects() {
	        return projectRepository.findAll();
	    }

	    public Optional<Project> findProjectById(Long id) {
	        return projectRepository.findById(id);
	    }
	    @Transactional
	    public void assignEmployee(Long projectId, Long employeeId) {
	        Project project = projectRepository.findById(projectId).orElseThrow();
	        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

	        if (employee.getProjects().size() >= 2) {
	            throw new RuntimeException("Employee cannot be assigned to more than 2 projects.");
	        }

	        project.getEmployees().add(employee);
	        projectRepository.save(project);
	    }

	    @Transactional
	    public void removeEmployee(Long projectId, Long employeeId) {
	        Project project = projectRepository.findById(projectId).orElseThrow();
	        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

	        project.getEmployees().remove(employee);
	        projectRepository.save(project);
	    }

	    @Transactional
	    public void updateStatus(Long projectId, ProjectStatus status) {
	        Project project = projectRepository.findById(projectId).orElseThrow();
	        project.setStatus(status);
	        projectRepository.save(project);
	    }

	    @Transactional
	    public void setTeamLeader(Long projectId, Long employeeId) {
	        Project project = projectRepository.findById(projectId).orElseThrow();
	        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

	        project.setTeamLeader(employee);
	        projectRepository.save(project);
	    }
}
