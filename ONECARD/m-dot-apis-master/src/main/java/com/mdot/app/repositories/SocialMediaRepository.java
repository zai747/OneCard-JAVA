package com.mdot.app.repositories;

import java.util.List;
import java.util.Optional;

import com.mdot.app.models.RecordStatus;
import com.mdot.app.models.Socialmedia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialMediaRepository extends JpaRepository <Socialmedia,Long>{
    
    Optional<Socialmedia> findByUser(long id);

    List<Socialmedia> findByUserIdAndStatus(long id, RecordStatus recordStatus);


}
