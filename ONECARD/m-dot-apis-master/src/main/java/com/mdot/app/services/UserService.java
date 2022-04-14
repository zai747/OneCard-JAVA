package com.mdot.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.mdot.app.models.RecordStatus;
import com.mdot.app.models.RoleName;
import com.mdot.app.models.User;
import com.mdot.app.payloads.requests.UserRequest;
import com.mdot.app.payloads.requests.UserUpdateRequest;
import com.mdot.app.payloads.responses.ApiResponse;
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


	@Transactional
	public ResponseEntity<?> save(@Valid UserRequest userRequest) {
		try {
			User user = new User();

			user.setId((long) 0);
			user.setUsername(userRequest.getUsername());
			user.setPhone(userRequest.getPhone());
			user.setEmail(userRequest.getEmail());
			user.setPassword(userRequest.getPassword());
			user.setJobtitle(userRequest.getJobtitle());
			user.setProfileimage(userRequest.getProfileimage());
			user.setDescription(userRequest.getDescription());
			user.setUsermedia(userRequest.getUsermedia());
			user.setProjects(userRequest.getProjects());
			user.setStatus(RecordStatus.ACTIVE);
			user = this.userRepository.save(user);

			return new ResponseEntity<>(new ApiResponse(true, "Saved successfully", user), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}		

	}

	@Transactional
	public ResponseEntity<?> list() {
		try {
			List<User> list = this.userRepository.findAll();
			return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}



@Transactional
public ResponseEntity<?> listById(long id) {
	try {
		Optional<User> user = this.userRepository.findById(id);

		if (!user.isPresent())
			return new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(new ApiResponse(true, "", user), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
	}

}

@Transactional
public ResponseEntity<?> listByUsername(String username) {
	try {
		Optional<User> user = this.userRepository.findByUsername(username);

		if (!user.isPresent())
			return new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(new ApiResponse(true, "", user), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);}
	}

	@Transactional
	public ResponseEntity<?> listByPhone(String phone) {
		try {
			Optional<User> user = this.userRepository.findByPhone(phone);

			if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			return new ResponseEntity<>(new ApiResponse(true, "", user), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);}
		}

		@Transactional
	public ResponseEntity<?> listByEmail(String email) {
		try {
			Optional<User> user = this.userRepository.findByEmail(email);

			if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			return new ResponseEntity<>(new ApiResponse(true, "", user), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);}
		}

		public ResponseEntity<?> delete(long id) {
			try {
				this.userRepository.deleteById(id);
				return new ResponseEntity<>(new ApiResponse(true, "Deleted successfully"), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
			}

		}

		public ResponseEntity<?> update(long id, @Valid UserUpdateRequest userRequest) {
			try {
				Optional<User> user = this.userRepository.findById(id);
	
				if (!user.isPresent())
					return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);
	
	
				user.get().setPhone(userRequest.getPhone());
				user.get().setEmail(userRequest.getEmail());
				//user.get().setFirstname(userRequest.getFirstname());
				//user.get().setLastname(userRequest.getLastname());
				user.get().setJobtitle(userRequest.getJobtitle());
	
				user.get().setDescription(userRequest.getDescription());
				user.get().setUsermedia(userRequest.getUsermedia());
				user.get().setProjects(userRequest.getProjects());
				user.get().setProfileimage(userRequest.getProfileimage());
				user.get().setStatus(RecordStatus.ACTIVE);

			
				return new ResponseEntity<>(
						new ApiResponse(true, "Updated successfully", this.userRepository.save(user.get())),
						HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);}
			}

			
	public ResponseEntity<?> updateImage(long uid, String image) {
		try {
			Optional<User> user = this.userRepository.findById(uid);

			if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			user.get().setProfileimage(image);

			return new ResponseEntity<>(
					new ApiResponse(true, "Updated successfully", this.userRepository.save(user.get())),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}

	}

@Transactional
public ResponseEntity<?> listByStatus(long id, String status) {
	try {
		RecordStatus recordStatus = RecordStatus.ACTIVE;

		switch (status) {
			case "INACTIVE":
				recordStatus = RecordStatus.INACTIVE;
				break;
			case "DELETED":
				recordStatus = RecordStatus.DELETED;
				break;
			default:
				recordStatus = RecordStatus.ACTIVE;
				break;
		}

		List<User> list = this.userRepository.findByStatusAndEnabled(id, recordStatus);
		return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
	}
	
}}

	