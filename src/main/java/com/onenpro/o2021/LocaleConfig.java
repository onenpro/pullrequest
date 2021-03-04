package com.onenpro.o2021;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {

	@Value( "${messages.basename}" )
	private String basename;
	@Value( "${messages.seconds}" )
	private int seconds;
	@Value( "${locale.default}" )
	private String defaultLocale;
	
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
	@Bean  
	public  LocaleResolver localeResolver()  
	{  
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale(defaultLocale));
		return localeResolver;  
	}
	
	@Bean  
	public MessageSource messageSource()  
	{  
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource(); 
		messageSource.setBasename(basename);
		messageSource.setCacheSeconds(seconds);
		return messageSource;  
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}
}
