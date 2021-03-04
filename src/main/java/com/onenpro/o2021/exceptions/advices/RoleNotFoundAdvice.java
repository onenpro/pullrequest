package com.onenpro.o2021.exceptions.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.onenpro.o2021.exceptions.RoleNotFoundException;

@ControllerAdvice
public class RoleNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(RoleNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String roleNotFoundHandler(RoleNotFoundException ex) {
	  return ex.getMessage();
	}
}
