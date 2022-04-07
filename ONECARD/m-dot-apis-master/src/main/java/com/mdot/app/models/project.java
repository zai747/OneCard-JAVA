package com.mdot.app.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Project", uniqueConstraints = { @UniqueConstraint(columnNames = { }), })
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Project extends DateAudit {

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
    
    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user", nullable = true)
    private User user;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private WideRecordStatus status = WideRecordStatus.ACTIVE;

}
