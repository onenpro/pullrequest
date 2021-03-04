package com.onenpro.o2021.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class Role {

	@Schema(description = "Identificador unico del role")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Schema(description = "Nombre del role")
	private String name;

	@Schema(description = "Role activo o no")
	private Boolean enabled;
	
	@JsonIgnore
	@Schema(description = "Usuarios que tienen el role")
	@OneToMany(mappedBy="role")
	private Set<User> users;
	
	@JsonIgnore
	@Schema(description = "Privilegios que tiene el rol")
  	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Role_Privilege", joinColumns = { @JoinColumn(name = "idrole", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "idprivilege", nullable = false) })
  	private Set<Privilege> privileges;
    
	public Role() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	
}
