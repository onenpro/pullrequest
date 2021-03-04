package com.onenpro.o2021.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {

	@Autowired   
	private MessageSource messageSource; 
	
	
	@Value( "${locale.default}" )
	private String defaultLocale;
	
	@GetMapping("/ping")
	public String ping(Locale locale, HttpServletRequest request)
	{   Logger logger = LogManager.getLogger("audit");
    	logger.info("IP " + request.getRemoteAddr() + ": hizo ping");
		return messageSource.getMessage("pong", null, locale);  
	}  
}
