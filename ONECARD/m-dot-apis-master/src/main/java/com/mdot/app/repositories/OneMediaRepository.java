package com.mdot.app.repositories;


import java.util.Optional;

import com.mdot.app.models.OneMedia;

import org.springframework.data.jpa.repository.JpaRepository;



public interface OneMediaRepository extends JpaRepository<OneMedia, Long> {


    Optional<OneMedia> findByid(long id);

}