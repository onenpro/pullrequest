package com.onenpro.o2021.exceptions;

public class RoleNotFoundException extends RuntimeException {


	public RoleNotFoundException(Long id){
		 super("Could not find role " + id);
	}
}
