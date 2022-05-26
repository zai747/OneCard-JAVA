package com.mdot.app.repositories;


import java.util.List;
import java.util.Optional;

import com.mdot.app.models.OneMedia;
import com.mdot.app.models.RecordStatus;

import org.springframework.data.jpa.repository.JpaRepository;



public interface OneMediaRepository extends JpaRepository<OneMedia, Long> {


    Optional<OneMedia> findByid(long id);

    List<OneMedia> findByUser(long id);

    List<OneMedia> findByUserIdAndStatus(long id, RecordStatus recordStatus);



}