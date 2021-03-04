package com.onenpro.o2021.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootTest
public class MainControllerTest extends AbstractTestNGSpringContextTests{

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeClass
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void ping_en() throws Exception {
		mockMvc.perform(get("/ping?lang=en")).andExpect(status().isOk())
				.andExpect(content().string("pong (en)"));

	}
	
	//TODO (Fix in CI)
    //@Test
	public void ping_es() throws Exception {
		mockMvc.perform(get("/ping?lang=es")).andExpect(status().isOk())
				.andExpect(content().string("pong (es)"));

	}
}
