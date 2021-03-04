package com.onenpro.o2021.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onenpro.o2021.domain.User;
import com.onenpro.o2021.exceptions.UserNotFoundException;
import com.onenpro.o2021.filters.UserFilter;
import com.onenpro.o2021.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping
	public User create(@RequestBody User user) {
		return userService.save(user);
	}
	
	@PutMapping
	public User update(@RequestBody User user, @RequestParam Long id) {
		User oldUser = userService.findById(id);
		if((oldUser == null) || (!oldUser.isDeleted())) { 
			return userService.save(user);
		}
		return null;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		userService.delete(id);
	}
	
	@GetMapping("/{id}")
	public User get(@PathVariable Long id) {
		User user = userService.findByIdAndNotDeleted(id);
		if( user == null) {
			throw new UserNotFoundException(id);
		}
		return user;
	}
	
	@GetMapping("/find")
	public Page<User> find(UserFilter userFilter, String order,  Integer pageNumber){
		
		return userService.find(userFilter, order, pageNumber);
	}
	
}
