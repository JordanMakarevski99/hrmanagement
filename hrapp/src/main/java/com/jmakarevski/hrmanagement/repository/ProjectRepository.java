package com.jmakarevski.hrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmakarevski.hrmanagement.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
