package com.mdot.app.repositories;

import com.mdot.app.models.Socialmedia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialMediaRepository extends JpaRepository <Socialmedia,Long>{
    
}
