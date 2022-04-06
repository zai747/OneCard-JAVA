package com.mdot.app.repositories;


import org.springframework.data.jpa.repository.JpaRepository;


import com.mdot.app.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	
}