package com.onenpro.o2021.repository.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.onenpro.o2021.domain.Role;

public class RoleSpecifications {
	
	public Specification<Role> findByName(String name) {
	    return (user, cq, cb) -> cb.like(user.get("name"), "%" + name + "%");
	}

	public Specification<Role> findByEnabled(Boolean enabled) {
	    return (user, cq, cb) -> cb.equal(user.get("enabled"), enabled);
	}
}
