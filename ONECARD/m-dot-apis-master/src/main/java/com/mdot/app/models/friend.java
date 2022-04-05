package com.mdot.app.models;


import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "friend", uniqueConstraints = { @UniqueConstraint(columnNames = { }), })
@Getter
@Setter
@NoArgsConstructor
@ToString
public class friend extends DateAudit {

	private static final long serialVersionUID = -8361939544099438297L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user", nullable = true)
    private User user;

}
