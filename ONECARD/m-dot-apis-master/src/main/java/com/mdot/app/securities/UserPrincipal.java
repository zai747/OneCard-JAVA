package com.mdot.app.securities;


import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.mdot.app.models.User;

import lombok.Getter;

@Getter
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 4902399377008590826L;

	private Long id;

	private String username;

	@JsonIgnore
	private String password;

   
	private String jobtitle;

	private String email;

	private String phone;


	private String profileimage;

	private String usermedia;

	
	private String description;


	
	private String projects;

	
	private String status;

	private Boolean secure;



	@JsonIgnore
	private boolean enabled;

	@JsonIgnore
	private boolean locked;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();

		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.description =user.getDescription();
		this.jobtitle = user.getJobtitle();
		this.usermedia = user.getUsermedia();
		 this.profileimage = user.getProfileimage();
		 this.projects = user.getProjects();
		// this.gender = user.getGender();
		// this.dob = user.getDob();
		// this.address = user.getAddress();
		// this.city = user.getCity();
		// this.state = user.getState();
		// this.country = user.getCountry();
		// this.pin = user.getPin();
		// this.image = user.getImage();
		// this.creditLimit = user.getCreditLimit();
		// this.balance = user.getBalance();
		// this.totalPayment = user.getTotalPayment();
		// this.stripeCustomerId = user.getStripeCustomerId();
	
		this.enabled = user.isEnabled();
		// this.locked = user.isLocked();
		this.authorities = authorities;
	}

	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserPrincipal(user, authorities);
	}

	public Long getId() {
		return id;
	}
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}

	
}