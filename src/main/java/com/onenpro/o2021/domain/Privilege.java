package com.onenpro.o2021.domain;

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
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class Privilege {

	@Schema(description = "Identificador unico del privilege")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private Long id;
	
	@NotNull
	@Schema(description = "Codigo del privilege")
	@Column(nullable=false, length=50)
	private String code;
	
	@Schema(description = "Codigo para facilitar la internacionalizacion")
	@Column(nullable=false, length=50)
	private String i18n;
	
	@Schema(description = "el orden en el que se va a mostrar")
	@Column(name="`order`")
	private Integer order;
	
	@Schema(description = "utilizado para saber si está activo el privilegio")
	@Column(nullable=false)
	private Boolean enabled;
	
	@Schema(description = "elación ManyToOne, para relacionarlo con un grupo de privilegios")
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "idprivilegegroup", referencedColumnName = "id")
	private PrivilegeGroup privilegeGroup;
    
	@Schema(description = "relación Many to Many, lista de Roles que tienen ese privilegio")
  	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Role_Privilege", joinColumns = { @JoinColumn(name = "idprivilege", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "idrole", nullable = false) })
  	private Set<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getI18n() {
		return i18n;
	}

	public void setI18n(String i18n) {
		this.i18n = i18n;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public PrivilegeGroup getPrivilegeGroup() {
		return privilegeGroup;
	}

	public void setPrivilegeGroup(PrivilegeGroup privilegeGroup) {
		this.privilegeGroup = privilegeGroup;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
}
