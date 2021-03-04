package com.onenpro.o2021.service;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.onenpro.o2021.domain.User;
import com.onenpro.o2021.repository.UserRepository;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class UserServiceImplTest extends AbstractTestNGSpringContextTests{
	
	@Mock
	private UserRepository userRepository;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@InjectMocks
	private UserServiceImpl userService;

	
	@BeforeMethod
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}


	@Test
	public void save_OK() {
		User testUser = new User();
		testUser.setUsername("testUser");
		
		Mockito.when(userRepository.save(testUser)).thenReturn(testUser);
		
		User created = userService.save(testUser);
		Assert.assertEquals(created.getUsername(), testUser.getUsername());
		Mockito.verify(userRepository).save(testUser);
	}
	
	@Test
	public void findById_OK() {
		User findByIdOK = new User();
		findByIdOK.setUsername("findById_OK");
		
		Optional<User> optionalUser = Optional.of(findByIdOK);
		
		Mockito.when(userRepository.findById(1L)).thenReturn(optionalUser);
		
		User findId1 = userService.findById(1L);
		Assert.assertEquals(findId1.getUsername(), findByIdOK.getUsername());
		Mockito.verify(userRepository).findById(1L);
	}
	
	@Test
	public void findById_IdNotFound() {
		Optional<User> optionalNull = Optional.ofNullable(null);
		
		Mockito.when(userRepository.findById(2L)).thenReturn(optionalNull);
		
		User findByIdFail = userService.findById(2L);
		Assert.assertEquals(findByIdFail, null);
		Mockito.verify(userRepository).findById(2L);
	}
	
	
	//TODO @Test
	public void delete_UserFoundButFail() {
		User userFoundButFail = new User();
		Optional<User> optionaluserFoundButFail = Optional.of(userFoundButFail);
		Mockito.when(userRepository.findById(4L)).thenReturn(optionaluserFoundButFail);
		Mockito.when(userRepository.save(userFoundButFail)).thenReturn(userFoundButFail);
		userService.delete(4L);
		
		Mockito.verify(userRepository).findById(4L);
		Mockito.verify(userRepository).deleteById(4L);
		Mockito.verify(userRepository).save(userFoundButFail);
	}
	
	@Test
	public void delete_OK() {
		userService.delete(5L);
		Mockito.verify(userRepository).deleteById(5L);
	}
	
	@Test
	public void findByIdAndNotDeleted_OK() {
		User user = new User();
		user.setDeleted(false);
		Optional<User> optionalUser = Optional.of(user);
		Mockito.when(userRepository.findById(1L)).thenReturn(optionalUser);
		
		Assert.assertEquals(user, userService.findByIdAndNotDeleted(1L));
		
		Mockito.verify(userRepository).findById(1L);
	}
	
	@Test
	public void findByIdAndNotDeleted_NotFound() {
		Optional<User> optionalUser = Optional.ofNullable(null);
		Mockito.when(userRepository.findById(1L)).thenReturn(optionalUser);
		
		Assert.assertEquals(null, userService.findByIdAndNotDeleted(1L));
		
		Mockito.verify(userRepository).findById(1L);
	}
	
	@Test
	public void findByIdAndNotDeleted_FoundButDeleted() {
		User user = new User();
		user.setDeleted(true);
		Optional<User> optionalUser = Optional.of(user);
		Mockito.when(userRepository.findById(1L)).thenReturn(optionalUser);
		
		Assert.assertEquals(null, userService.findByIdAndNotDeleted(1L));
		
		Mockito.verify(userRepository).findById(1L);
	}
	
}
