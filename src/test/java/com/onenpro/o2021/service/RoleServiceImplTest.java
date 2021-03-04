package com.onenpro.o2021.service;


import java.util.ArrayList;
import java.util.List;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.onenpro.o2021.domain.Role;
import com.onenpro.o2021.repository.RoleRepository;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class RoleServiceImplTest extends AbstractTestNGSpringContextTests{

	@Mock
	private RoleRepository roleRepository;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@InjectMocks
	private RoleServiceImpl roleService;

	
	@BeforeClass
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void save_OK() {
		Role testRole = new Role();
		testRole.setName("testRole");
		
		Mockito.when(roleRepository.save(testRole)).thenReturn(testRole);
		
		Role created = roleService.save(testRole);
		Assert.assertEquals(created.getName(), testRole.getName());
		Mockito.verify(roleRepository).save(testRole);
	}
	
	@Test
	public void findById_OK() {
		Role findByIdOK = new Role();
		findByIdOK.setName("findById_OK");
		Optional<Role> optionalRole = Optional.of(findByIdOK);
		
		Mockito.when(roleRepository.findById(1L)).thenReturn(optionalRole);
		
		Role findId1 = roleService.findById(1L);
		Assert.assertEquals(findId1.getName(), findByIdOK.getName());
		
		Mockito.verify(roleRepository).findById(1L);
	}
	
	@Test
	public void findById_IdNotFound() {
		Optional<Role> optionalNull = Optional.ofNullable(null);
		
		Mockito.when(roleRepository.findById(2L)).thenReturn(optionalNull);
		
		Role findByIdFail = roleService.findById(2L);
		Assert.assertEquals(findByIdFail, null);
		
		Mockito.verify(roleRepository).findById(2L);
	}
	
	@Test
	public void delete_OK() {
		roleService.delete(4L);
		
		Mockito.verify(roleRepository).deleteById(4L);
	}
	
	@Test
	public void findAll_OK() {
		Role role = new Role ();
		role.setEnabled(true);
		role.setName("testFindAll");
		List<Role> lista = new ArrayList<>();
		lista.add(role);
		
		Mockito.when(roleRepository.findAll()).thenReturn(lista);
		
		Assert.assertEquals(lista, roleService.findAll());
		
		Mockito.verify(roleRepository).findAll();
	}
}
