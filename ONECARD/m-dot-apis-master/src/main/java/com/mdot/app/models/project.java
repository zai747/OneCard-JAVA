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
@Table(name = "projects", uniqueConstraints = { @UniqueConstraint(columnNames = { }), })
@Getter
@Setter
@NoArgsConstructor
@ToString
public class project extends DateAudit {

	private static final long serialVersionUID = -8361939544099438297L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 30)
	@Column(name = "title", nullable = false)
	private String title;

    @Size(max = 200)
	@Column(name = "image", nullable = true)
	private String image = "";


    
    @Size(max = 200)
	@Column(name = "descriptiopn", nullable = true)
	private String description;
    
    
    

}
