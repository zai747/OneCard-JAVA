package com.mdot.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(length = 60, nullable = false)
	private RoleName name;

}
