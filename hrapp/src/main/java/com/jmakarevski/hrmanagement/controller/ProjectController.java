package com.jmakarevski.hrmanagement.controller;
import com.jmakarevski.hrmanagement.dto.*;
import com.jmakarevski.hrmanagement.model.*;
import com.jmakarevski.hrmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
	
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.findAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.findProjectById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectDto projectRequest) {
        Project project = projectService.createProject(
                projectRequest.getName(),
                projectRequest.getDescription(),
                projectRequest.getStartDate(),
                projectRequest.getEndDate()
        );
        return ResponseEntity.ok(project);
    }
    
    @PostMapping("/{id}/assign")
    public ResponseEntity<Void> assignEmployee(@PathVariable Long id, @RequestBody ProjectAssignmentDto dto) {
        projectService.assignEmployee(id, dto.getEmployeeId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/remove")
    public ResponseEntity<Void> removeEmployee(@PathVariable Long id, @RequestBody ProjectAssignmentDto dto) {
        projectService.removeEmployee(id, dto.getEmployeeId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody ProjectStatus status) {
        projectService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/team-leader")
    public ResponseEntity<Void> setTeamLeader(@PathVariable Long id, @RequestBody Long employeeId) {
        projectService.setTeamLeader(id, employeeId);
        return ResponseEntity.ok().build();
    }
}
