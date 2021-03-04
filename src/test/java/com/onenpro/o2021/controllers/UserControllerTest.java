package com.onenpro.o2021.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.util.NestedServletException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onenpro.o2021.domain.User;
import com.onenpro.o2021.service.UserService;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class UserControllerTest extends AbstractTestNGSpringContextTests{

	private MockMvc mockMvc;

	@InjectMocks
	private UserController userController;

	@Spy
	private UserService userService;

	
	@BeforeMethod
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setValidator(new LocalValidatorFactoryBean()).build();
	}

	@Test
	public void create_OK() throws Exception {
		
		User testUser = new User();
		testUser.setUsername("testUser");
		testUser.setPassword("testUser");
		testUser.setEmail("testUser");
		
		Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(testUser);
		
		mockMvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(testUser))
                .contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(content().json(asJsonString(testUser)));
		
		Mockito.verify(userService).save(Mockito.any(User.class));
	}
	
	//TODO @Test //(expectedExceptions = ConstraintViolationException.class)
	public void create_WithoutAllRequiredData() throws Exception {
		
		User testUser = new User();
		
		Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(testUser);
		
		mockMvc.perform(post("/user")
				.content(asJsonString(testUser))
                .contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				   .andExpect(content().json(asJsonString(testUser)));
		
		Mockito.verify(userService).save(Mockito.any(User.class));
	}
	
	@Test
	public void update_OKExists() throws Exception {
		
		User testUser = new User();
		testUser.setUsername("testUser");
		testUser.setPassword("testUser");
		testUser.setEmail("testUser");
		
		User testUserOld = new User();
		testUserOld.setUsername("testUserOld");
		
		Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(testUser);
		Mockito.when(userService.findById(1L)).thenReturn(testUserOld);
		
		mockMvc.perform(put("/user?id=1")
				.content(asJsonString(testUser))
                .contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				   .andExpect(content().json(asJsonString(testUser)));
		
		
		Mockito.verify(userService).save(Mockito.any(User.class));
		Mockito.verify(userService).findById(1L);
	}
	
	@Test
	public void update_OKuserNotFound() throws Exception {
		
		User testUser = new User();
		testUser.setUsername("testUser");
		testUser.setPassword("testUser");
		testUser.setEmail("testUser");
		
		Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(testUser);
		Mockito.when(userService.findById(2L)).thenReturn(null);
		
		mockMvc.perform(put("/user?id=2")
				.content(asJsonString(testUser))
                .contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(asJsonString(testUser)));
		
		Mockito.verify(userService).save(Mockito.any(User.class));
		Mockito.verify(userService).findById(2L);
	}

	@Test
	public void update_FailIsDeleted() throws Exception {
		
		User testUser = new User();
		testUser.setUsername("testUser");
		testUser.setPassword("testUser");
		testUser.setEmail("testUser");
		testUser.setDeleted(true);
		
		Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(null);
		Mockito.when(userService.findById(1L)).thenReturn(testUser);
		
		mockMvc.perform(put("/user?id=1")
				.content(asJsonString(testUser))
                .contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
				   .andExpect(content().string(""));
		
		Mockito.verify(userService).findById(1L);
		Mockito.verify(userService, Mockito.times(0)).save(Mockito.any(User.class));
		
	}
	
	@Test
	public void delete_OK() throws Exception {
		mockMvc.perform(delete("/user/2"))
			.andExpect(status().isOk());
		Mockito.verify(userService).delete(2L);
	}
	
	@Test
	public void get_OK() throws Exception {
		
		User testUser = new User();
		testUser.setUsername("testUser");
		testUser.setPassword("testUser");
		testUser.setEmail("testUser");
		
		Mockito.when(userService.findByIdAndNotDeleted(2L)).thenReturn(testUser);
		
		
		mockMvc.perform(get("/user/2"))
			.andExpect(status().isOk())
			.andExpect(content().string(asJsonString(testUser)));
		Mockito.verify(userService).findByIdAndNotDeleted(2L);
	}
	
	
	
	@Test(expectedExceptions = NestedServletException.class)
	public void get_Null() throws Exception {
		Mockito.when(userService.findByIdAndNotDeleted(2L)).thenReturn(null);
		
		mockMvc.perform(get("/user/2"))
			.andExpect(status().isNotFound())
			.andExpect(content().string(""));
		Mockito.verify(userService).findByIdAndNotDeleted(2L);
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
