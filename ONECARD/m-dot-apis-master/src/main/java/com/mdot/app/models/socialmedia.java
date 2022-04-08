package com.mdot.app.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import com.mdot.app.models.audit.DateAudit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Socialmedia", uniqueConstraints = { @UniqueConstraint(columnNames = { }), })
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Socialmedia extends DateAudit {

	private static final long serialVersionUID = -8361939544099438297L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 30)
	@Column(name = "instagram", nullable = false)
	private String instagram;

    @Size(max = 200)
	@Column(name = "facebook", nullable = true)
	private String facebook;


    
    @Size(max = 200)
	@Column(name = "twitter", nullable = true)
	private String twitter;

    @Size(max = 200)
	@Column(name = "snapchat", nullable = true)
	private String snapchat;

    @Size(max = 200)
	@Column(name = "linkedin", nullable = true)
	private String linkedin;

    @Size(max = 200)
	@Column(name = "pinterest", nullable = true)
	private String pinterest;


    @Size(max = 200)
	@Column(name = "twitch", nullable = true)
	private String twitch;
    
    @Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private RecordStatus status = RecordStatus.ACTIVE;
    

}
