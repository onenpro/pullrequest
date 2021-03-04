package com.onenpro.o2021.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.onenpro.o2021.domain.User;
import com.onenpro.o2021.service.RoleService;

import net.minidev.json.JSONArray;



@SpringBootTest
@ActiveProfiles(profiles = "test")
@Sql(scripts= {"/schema.sql"})
public class UserControllerIntegrationTest extends AbstractTestNGSpringContextTests{
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private RoleService roleService;

	private MockMvc mockMvc;

	@BeforeClass
	public void setup() {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
	}
	
	@Test
	public void find_withoutFilter() throws Exception{
		List<User> users = generateUsers();
		MvcResult result = mockMvc.perform(get("/user/find?order=null&pageNumber=0"))
				.andExpect(status().isOk())
				.andReturn();
		
		
		JSONArray content = JsonPath.read(result.getResponse().getContentAsString(), "$.content");
		Assert.assertEquals(content.toString(), asJsonString(users));

	}
	
	@Test
	public void find_withUsernameFilter() throws Exception{
		List<User> expectedUsers = new ArrayList<>();
		generateUsers().forEach(u -> {
			if (u.getUsername().contains("user")) expectedUsers.add(u);
		});
		
		MvcResult result= mockMvc.perform(get("/user/find?username=user&order=null&pageNumber=0"))
			.andExpect(status().isOk())
				.andReturn();
		
		System.out.println("Test expected:"+ asJsonString(expectedUsers));
		JSONArray content = JsonPath.read(result.getResponse().getContentAsString(), "$.content");
		Assert.assertEquals(content.toString(), asJsonString(expectedUsers));

		
	}
	
	@Test
	public void find_withRoleFilter() throws Exception{
		List<User> expectedUsers = new ArrayList<>();
		generateUsers().forEach(u -> {
			if (u.getRole().getId()==2L) expectedUsers.add(u);
		});
		
		MvcResult result = mockMvc.perform(get("/user/find?roleId=2&order=null&pageNumber=0"))
				.andExpect(status().isOk())
				.andReturn();
		
		JSONArray content = JsonPath.read(result.getResponse().getContentAsString(), "$.content");
		Assert.assertEquals(content.toString(), asJsonString(expectedUsers));
	}
	
	@Test
	public void find_withEnabledFilter() throws Exception{
		List<User> expectedUsers = new ArrayList<>();
		generateUsers().forEach(u -> {
			if (u.isEnabled()) expectedUsers.add(u);
		});

		MvcResult result = mockMvc.perform(get("/user/find?enabled=true&order=null&pageNumber=0"))
				.andExpect(status().isOk())
				.andReturn();
		
		JSONArray content = JsonPath.read(result.getResponse().getContentAsString(), "$.content");
		Assert.assertEquals(content.toString(), asJsonString(expectedUsers));
	}
	
	@Test
	public void find_withEmailFilter() throws Exception{
		List<User> expectedUsers = new ArrayList<>();
		generateUsers().forEach(u -> {
			if (u.getEmail().contains("aemail")) expectedUsers.add(u);
		});
		
		MvcResult result = mockMvc.perform(get("/user/find?email=aemail&order=null&pageNumber=0"))
				.andExpect(status().isOk())
				.andReturn();
		
		JSONArray content = JsonPath.read(result.getResponse().getContentAsString(), "$.content");
		Assert.assertEquals(content.toString(), asJsonString(expectedUsers));
	}
	
	@Test
	public void find_withoutFilterWithOrderByEmail() throws Exception{
		List<User> expectedUsers = generateUsers().stream().sorted((o1, o2)->o1.getEmail().
                compareTo(o2.getEmail())).
                collect(Collectors.toList());
		MvcResult result = mockMvc.perform(get("/user/find?order=email&pageNumber=0"))
				.andExpect(status().isOk())
				.andReturn();
		
		
		JSONArray content = JsonPath.read(result.getResponse().getContentAsString(), "$.content");
		Assert.assertEquals(content.toString(), asJsonString(expectedUsers));

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
	
	private List<User> generateUsers() {
		List<User> users = new ArrayList<>();
		users.add(createUser(1L, false, "femail@gmail.com", true, "1117", "nora", 1L));
		users.add(createUser(2L, false, "bemail@gmail.com", false, "1117", "admin", 4L));
		users.add(createUser(4L, false, "cemail@gmail.com", false, "1117", "usernora", 2L));
		users.add(createUser(5L, false, "aemail@gmail.com", true, "1117", "adminuser", 2L));
		return users;
	}

	private User createUser(Long id, boolean deleted, String email, boolean enabled, String password, String username, Long idrole) {
		User user = new User();
		user.setId(id);
		user.setDeleted(deleted);
		user.setEmail(email);
		user.setEnabled(enabled);
		user.setPassword(password);
		user.setUsername(username);
		user.setRole(roleService.findById(idrole));
		return user;
	}
}
