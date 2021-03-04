package com.onenpro.o2021.service;


import org.springframework.data.domain.Page;

import com.onenpro.o2021.domain.User;
import com.onenpro.o2021.filters.UserFilter;


public interface UserService {
	
	public User save(User user);
	public User findByIdAndNotDeleted(Long id);
	public User findById(Long id);
	public void delete(Long id);
	public Page<User> find(UserFilter form, String order, int pageNumber);
}
