package com.onenpro.o2021.aspects;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Aspect
public class PostRequestLogger {

	private static final Logger logger = LogManager.getLogger("audit");
	
	@Before("@annotation(org.springframework.web.bind.annotation.PostMapping) or @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void logActionPutBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (null != request) {
        	logger.trace("Usuario: " + request.getUserPrincipal());
        	logger.trace("Endpoint: " + request.getServletPath());
        	logger.trace("Tipo de metodo: " + request.getMethod());
        }
        Object[] signatureArgs = joinPoint.getArgs();
        logger.trace("Parametros de entrada: ");
        for (Object signatureArg: signatureArgs) {
        	logger.trace(signatureArg.getClass()+ ": " + asJsonString(signatureArg));
        }
    }
	
	@AfterReturning(value= "@annotation(org.springframework.web.bind.annotation.PostMapping) or "
			+ "@annotation(org.springframework.web.bind.annotation.PutMapping)", returning = "returnValue")
    public void logActionPostAfter(JoinPoint joinPoint, Object returnValue) {
		logger.trace("Parametros de salida: " + asJsonString(returnValue));
    }
	
	private static String asJsonString(Object obj) {
		try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
