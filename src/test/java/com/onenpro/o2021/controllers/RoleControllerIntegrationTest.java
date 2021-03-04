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
import com.onenpro.o2021.domain.Role;

import net.minidev.json.JSONArray;



@SpringBootTest
@ActiveProfiles(profiles = "test")
@Sql(scripts= {"/schema.sql"})
public class RoleControllerIntegrationTest extends AbstractTestNGSpringContextTests{
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeClass
	public void setup() {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
	}
	
	@Test
	public void find_withoutFilter() throws Exception{
		List<Role> roles = generateRoles();
		MvcResult result = mockMvc.perform(get("/role/find?order=null&pageNumber=0"))
				.andExpect(status().isOk())
				.andReturn();
		
		
		JSONArray content = JsonPath.read(result.getResponse().getContentAsString(), "$.content");
		Assert.assertEquals(content.toString(), asJsonString(roles));

	}
	
	@Test
	public void find_withNameFilter() throws Exception{
		List<Role> expectedRoles = new ArrayList<>();
		generateRoles().forEach(r -> {
			if (r.getName().contains("admin")) expectedRoles.add(r);
		});
		
		MvcResult result= mockMvc.perform(get("/role/find?name=admin&order=null&pageNumber=0"))
			.andExpect(status().isOk())
				.andReturn();
		
		System.out.println("Test expected:"+ asJsonString(expectedRoles));
		JSONArray content = JsonPath.read(result.getResponse().getContentAsString(), "$.content");
		Assert.assertEquals(content.toString(), asJsonString(expectedRoles));

		
	}
	
	@Test
	public void find_withEnabledFilter() throws Exception{
		List<Role> expectedRoles = new ArrayList<>();
		generateRoles().forEach(r -> {
			if (r.isEnabled()) expectedRoles.add(r);
		});

		MvcResult result = mockMvc.perform(get("/role/find?enabled=true&order=null&pageNumber=0"))
				.andExpect(status().isOk())
				.andReturn();
		
		JSONArray content = JsonPath.read(result.getResponse().getContentAsString(), "$.content");
		Assert.assertEquals(content.toString(), asJsonString(expectedRoles));
	}
	
	@Test
	public void find_withoutFilterWithOrderByName() throws Exception{
		List<Role> expectedRoles = generateRoles().stream().sorted((o1, o2)->o1.getName().
                compareTo(o2.getName())).
                collect(Collectors.toList());
		MvcResult result = mockMvc.perform(get("/role/find?order=name&pageNumber=0"))
				.andExpect(status().isOk())
				.andReturn();
		
		
		JSONArray content = JsonPath.read(result.getResponse().getContentAsString(), "$.content");
		Assert.assertEquals(content.toString(), asJsonString(expectedRoles));

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
	
	private List<Role> generateRoles() {
		List<Role> roles = new ArrayList<>();
		roles.add(createRole(1L, true, "user"));
		roles.add(createRole(2L, true, "admin"));
		roles.add(createRole(3L, false, "user2false"));
		roles.add(createRole(4L, false, "admin2false"));
		return roles;
	}

	private Role createRole(Long id, boolean enabled, String name) {
		Role role = new Role();
		role.setEnabled(enabled);
		role.setName(name);
		role.setId(id);
		return role;
	}
}
