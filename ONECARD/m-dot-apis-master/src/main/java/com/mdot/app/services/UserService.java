package com.mdot.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdot.app.models.RoleName;
import com.mdot.app.models.User;
import com.mdot.app.repositories.RoleRepository;
import com.mdot.app.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Transactional
	public User save(User user) {
		user = this.userRepository.save(user);
		return user;
	}

	public boolean hasRole(RoleName role) {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role.toString()));
	}

	public boolean hasRole(User user, RoleName role) {
		return user.getRoles().stream().anyMatch(eachRole -> eachRole.getName().equals(role));
	}

}
