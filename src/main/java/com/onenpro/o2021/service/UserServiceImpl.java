package com.onenpro.o2021.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.onenpro.o2021.domain.User;
import com.onenpro.o2021.filters.UserFilter;
import com.onenpro.o2021.repository.UserRepository;
import com.onenpro.o2021.repository.specifications.UserSpecifications;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Value("${app.gui.paginator.size}")
	private int pagesize;

	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		Optional<User> optional = userRepository.findById(id);
		if(optional.isEmpty()) {
			return null;
		}
		return optional.get();
	}
	
	@Override
	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		}catch(InvalidDataAccessResourceUsageException p) {
			User user = userRepository.findById(id).get();
			user.setDeleted(true);
			userRepository.save(user);
		}
	}
	

	@Override
	public Page<User> find(UserFilter userFilter, String order, int pageNumber) {
		List<Specification<User>> especificaciones = new ArrayList<>();
		UserSpecifications userSpecifications = new UserSpecifications();
		Pageable pageable;
		
		especificaciones.add(userSpecifications.findByDeleted(false));
		System.out.println("Get username: " + userFilter.getUsername());
		
		if(userFilter.getUsername() != null) {
			especificaciones.add(userSpecifications.findByUsername(userFilter.getUsername()));
		}
		
		if(userFilter.getEmail() != null) {
			especificaciones.add(userSpecifications.findByEmail(userFilter.getEmail()));
		}
		
		if(userFilter.getEnabled() != null) {
			especificaciones.add(userSpecifications.findByEnabled(userFilter.getEnabled()));
		}
		if(userFilter.getRoleId() != null) {
			especificaciones.add(userSpecifications.findByRoleId(userFilter.getRoleId()));
		}
		
		if( (!order.equals("null"))  && (order != null)) {
			pageable = PageRequest.of(pageNumber, pagesize, Sort.by(order));
		}
		
		else {
			pageable = PageRequest.of(pageNumber, pagesize);
		}
		
		if(especificaciones.isEmpty()) {
			return userRepository.findAll(pageable) ;
		}
		
		return userRepository.findAll(build(especificaciones),pageable) ;
	}
	

	private Specification<User> build(List<Specification<User>> specs) {
        
        Specification<User> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
        	result = Specification.where(result).and(specs.get(i));
        }       
        return result;
    }
	@Override
	public User findByIdAndNotDeleted(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			if(!user.isDeleted()) {
				return user;
			}
		}
		return null;
	}

}
