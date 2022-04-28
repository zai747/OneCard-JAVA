package com.mdot.app.repositories;


import java.util.Optional;

import com.mdot.app.models.Card;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CardRepository extends JpaRepository<Card, Long> {


    Optional<Card> findByid(long id);

}