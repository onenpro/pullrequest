package com.onenpro.o2021.repository.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.onenpro.o2021.domain.Role;
import com.onenpro.o2021.domain.User;

public class UserSpecifications {
	
	public Specification<User> findByUsername(String username) {
	    return (user, cq, cb) -> cb.like(user.get("username"), "%" + username + "%");
	}
	
	public Specification<User> findByEmail(String email) {
	    return (user, cq, cb) -> cb.like(user.get("email"), "%" + email + "%");
	}
	
	public Specification<User> findByEnabled(Boolean enabled) {
	    return (user, cq, cb) -> cb.equal(user.get("enabled"), enabled);
	}
	
	public Specification<User> findByDeleted(Boolean deleted) {
	    return (user, cq, cb) -> cb.equal(user.get("deleted"), deleted);
	}
	
	public Specification<User> findByRoleId(Long roleId) {
	    return new Specification<User>() {
	        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            Join<User,Role> userRole = root.join("role");
	            return cb.equal(userRole.get("id"), roleId);
	        }
	    };
	}
	
}
