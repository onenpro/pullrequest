package com.onenpro.o2021.exceptions;

public class UserNotFoundException extends RuntimeException {


	public UserNotFoundException(Long id){
		 super("Could not find user " + id);
	}
}
