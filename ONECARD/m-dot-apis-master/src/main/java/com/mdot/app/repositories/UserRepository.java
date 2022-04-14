package com.mdot.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdot.app.models.RecordStatus;
import com.mdot.app.models.Role;
import com.mdot.app.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByUsername(String name);

	boolean existsByPhone(String phone);

	//boolean findByPhone(String phone);

	Optional<User> findByPhone(String phone);

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameAndEnabled(String username, boolean b);

	Optional<User> findByIdAndEnabled(long id, boolean b);

	List<User> findByRolesAndEnabled(Role role, boolean b);

	List<User> findByRolesAndEnabledAndPhoneContaining(Role role, boolean b, String phone);

	Optional<User> findById(long id);

    List<User> findByStatusAndEnabled(long id, RecordStatus recordStatus);

}