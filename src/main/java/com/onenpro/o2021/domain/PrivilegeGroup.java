package com.onenpro.o2021.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class PrivilegeGroup {

	@Schema(description = "Identificador unico del privilege group")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private Long id;
	
	@Schema(description = "Codigo para facilitar la internacionalizacion")
	@Column(nullable=false, length=50)
	private String i18n;
	
	@Schema(description = "el orden en el que se va a mostrar")
	@Column(name="`order`")
	private Integer order;
	
	@Schema(description = "el orden en el que se va a mostrar")
	@OrderBy(value="order ASC")
	@OneToMany(mappedBy="privilegeGroup", fetch=FetchType.LAZY)
	private Set<Privilege> privileges;
	
}	