package com.mdot.app.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdot.app.models.audit.DateAudit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }), })
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User extends DateAudit {

	private static final long serialVersionUID = -8361939544099438297L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 30)
	@Column(name = "username", nullable = false)
	private String username;


	@JsonIgnore
	@NotBlank
	@Size(max = 100)
	@Column(name = "password", nullable = false)
	private String password;

	

	

	@Size(max = 40)
	@Email
	@Column(name = "email", nullable = false)
	private String email = "";

	@Size(max = 15)
	@Column(name = "phone", nullable = false)
	private String phone = "";

	@Size(max = 250)
	@Column(name = "fcm_id", nullable = true)
	private String fcmId;

	@Column(name = "locked", nullable = false)
	private boolean locked = true;

	@Column(name = "enabled", nullable = false)
	private boolean enabled = true;

	@Size(max = 15)
	@Column(name = "description", nullable = false)
	private String description;

	@Size(max = 15)
	@Column(name = "jobtitle", nullable = false)
	private String jobtitle;

	@Size(max = 15)
	@Column(name = "usermedia", nullable = false)
	private String usermedia;



	@Column(name = "status", nullable = true)
	private String status;

	
	@Column(name = "projects", nullable = true)
	private String projects;



	@Column(name = "secure", nullable = false)
	private boolean secure = false;

	@Size(max = 200)
	@Column(name = "profileimage", nullable = true)
	private String profileimage = "";

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

}
