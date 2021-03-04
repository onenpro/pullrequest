package com.onenpro.o2021.service;


import java.util.List;

import org.springframework.data.domain.Page;

import com.onenpro.o2021.domain.Role;
import com.onenpro.o2021.filters.RoleFilter;


public interface RoleService {
	
	public Role save(Role role);
	public Role findById(Long id);
	public void delete(Long id);
	public Page<Role> find(RoleFilter roleFilter, String order, Integer pageNumber);
	public List<Role> findAll();
}
