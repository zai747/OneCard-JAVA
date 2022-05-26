package com.mdot.app.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.mdot.app.models.Friend;
import com.mdot.app.models.RecordStatus;

public interface FriendRepository extends JpaRepository<Friend, Long> {

	List<Friend> findByUserIdAndStatus(long id, RecordStatus recordStatus);

    List<Friend> findByUser(long id);

}