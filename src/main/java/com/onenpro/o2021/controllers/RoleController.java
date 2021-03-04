package com.onenpro.o2021.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onenpro.o2021.domain.Role;
import com.onenpro.o2021.exceptions.RoleNotFoundException;
import com.onenpro.o2021.filters.RoleFilter;
import com.onenpro.o2021.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	RoleService roleService;

	@PostMapping
	@ResponseBody
	public Role create(@RequestBody Role role) {
		return roleService.save(role);
	}
	
	@PutMapping
	public Role update(@RequestBody Role role) {
		return roleService.save(role);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		roleService.delete(id);
	}
	
	@GetMapping("/{id}")
	public Role get(@PathVariable Long id) {
		Role role = roleService.findById(id);
		if (role == null) {
			throw new RoleNotFoundException(id);
		}
		return role;
	}
	
	@GetMapping("/find")
	public Page<Role> find(RoleFilter roleFilter, String order, Integer pageNumber){
		
		return roleService.find(roleFilter, order, pageNumber);
	}
	
	@GetMapping("/findAll")
	public List<Role> findAll(){
		return roleService.findAll();
	}
}
