package com.onenpro.o2021.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onenpro.o2021.domain.Role;
import com.onenpro.o2021.service.RoleService;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class RoleControllerTest extends AbstractTestNGSpringContextTests{

	private MockMvc mockMvc;

	@Mock
	private RoleService roleService;

	@InjectMocks
	private RoleController roleController;
	
	@BeforeMethod
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
	}

	@Test
	public void create_OK() throws Exception {
		
		Role testCreateOK = new Role();
		testCreateOK.setName("testCreateOK");
		
		mockMvc.perform(post("/role")
				.content(asJsonString(testCreateOK))
                .contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				   .andExpect(content().json(asJsonString(testCreateOK)));
		
		Mockito.verify(roleService).save(Mockito.any(Role.class));
	}
	
	//@Test (expectedExceptions = ConstraintViolationException.class)
	public void create_WithoutAllRequiredData() throws Exception {
		
		Role testCreateFail = new Role();
		
		Mockito.when(roleService.save(Mockito.any(Role.class))).thenReturn(testCreateFail);
		
		mockMvc.perform(post("/role")
				.content(asJsonString(testCreateFail))
                .contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		Mockito.verify(roleService).save(Mockito.any(Role.class));
	}
	
	@Test
	public void update_OK() throws Exception {
		
		Role testUpdateOK = new Role();
		testUpdateOK.setName("testUpdateOK");
		
		Mockito.when(roleService.save(Mockito.any(Role.class))).thenReturn(testUpdateOK);
		
		mockMvc.perform(put("/role")
				.content(asJsonString(testUpdateOK))
                .contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				   .andExpect(content().string(asJsonString(testUpdateOK)));
		Mockito.verify(roleService).save(Mockito.any(Role.class));
	}
	
	@Test
	public void delete_OK() throws Exception {
		mockMvc.perform(delete("/role/2"))
			.andExpect(status().isOk());
		Mockito.verify(roleService).delete(2L);
	}
	
	@Test
	public void get_OK() throws Exception {
		Role testGetOK = new Role();
		testGetOK.setName("testGetOK");
		
		Mockito.when(roleService.findById(2L)).thenReturn(testGetOK);
		
		mockMvc.perform(get("/role/2"))
			.andExpect(status().isOk())
			.andExpect(content().string(asJsonString(testGetOK)));
		Mockito.verify(roleService).findById(2L);
	}
	
	//@Test(expectedExceptions = RoleNotFoundException.class)
	@Test(expectedExceptions = Exception.class)
	public void get_NotFound() throws Exception {
		
		Mockito.when(roleService.findById(2L)).thenReturn(null);
		
		mockMvc.perform(get("/role/2"))
			.andExpect(status().isOk())
			.andExpect(content().string(""));
		
		Mockito.verify(roleService).findById(2L);
	}
	
	@Test
	public void findAll_OK() throws Exception{
		Role role = new Role ();
		role.setEnabled(true);
		role.setName("testFindAll");
		List<Role> lista = new ArrayList<>();
		lista.add(role);
		lista.add(role);
		
		Mockito.when(roleService.findAll()).thenReturn(lista);
		
		mockMvc.perform(get("/role/findAll"))
		.andExpect(status().isOk())
		.andExpect(content().json(asJsonString(lista)));
		
		Mockito.verify(roleService).findAll();
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
