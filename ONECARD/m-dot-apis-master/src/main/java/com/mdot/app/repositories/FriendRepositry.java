package com.mdot.app.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mdot.app.models.Friend;

public interface FriendRepositry extends JpaRepository<Friend, Long> {

	
}