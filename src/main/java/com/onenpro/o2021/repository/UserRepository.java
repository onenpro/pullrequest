package com.onenpro.o2021.repository;

import org.springframework.stereotype.Repository;

import com.onenpro.o2021.domain.User;


@Repository
public interface UserRepository extends O2021Repository<User, Long>{
	public void deleteById(Long id);
}
