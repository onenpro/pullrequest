package com.onenpro.o2021.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name="`usuario`")
public class User {
	
	
	@Schema(description = "Identificador unico del user.", example = "1", required = true)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	@Schema(description = "Nombre de usuario")
    private String username;
    
	@NotNull
	@Schema(description = "Contraseña del usuario")
    private String password;
	
	@NotNull
	@Schema(description = "Email del usuario")
    private String email;
    
	@Schema(description = "User activo o no")
	private boolean enabled;
	
	@Schema(description = "User activo o no")
	@JsonIgnore
	private boolean deleted;
    
	@NotNull
    @Schema(description = "El role que tiene el usuario")
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idrole", referencedColumnName = "id")
    private Role role;
    
	
	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
