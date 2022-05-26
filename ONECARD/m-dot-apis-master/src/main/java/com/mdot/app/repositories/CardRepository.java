package com.mdot.app.repositories;


import java.util.List;
import java.util.Optional;

import com.mdot.app.models.Card;
import com.mdot.app.models.RecordStatus;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CardRepository extends JpaRepository<Card, Long> {


    Optional<Card> findByid(long id);

    List<Card> findByUserIdAndStatus(long id, RecordStatus recordStatus);


}