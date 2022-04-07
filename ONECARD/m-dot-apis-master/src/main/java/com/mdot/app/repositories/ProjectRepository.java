package com.mdot.app.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.mdot.app.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {



    List<Project> findByUserId(long id);

    //Optional<Project> findByUserIdAndType(Long id, String type);
	
}