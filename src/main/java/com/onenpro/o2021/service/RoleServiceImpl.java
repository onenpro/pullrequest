package com.onenpro.o2021.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.onenpro.o2021.domain.Role;
import com.onenpro.o2021.filters.RoleFilter;
import com.onenpro.o2021.repository.RoleRepository;
import com.onenpro.o2021.repository.specifications.RoleSpecifications;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Value("${app.gui.paginator.size}")
	private int pagesize;

	private RoleRepository roleRepository;
	
	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	@Override
	public Role save(Role role) {
		
		return roleRepository.save(role);
	}

	@Override
	public Role findById(Long id) {
		Optional<Role> optional = roleRepository.findById(id);
		if(optional.isEmpty()) {
			return null;
		}
		return optional.get();
	}
	
	@Override
	public void delete(Long id) {
		roleRepository.deleteById(id);
	}
	
	@Override
	public Page<Role> find(RoleFilter roleFilter, String order, Integer pageNumber) {
		List<Specification<Role>> especificaciones = new ArrayList<>();
		RoleSpecifications roleSpecifications = new RoleSpecifications();
		Pageable pageable;
		
		if(roleFilter.getName() != null) {
			especificaciones.add(roleSpecifications.findByName(roleFilter.getName()));
		}
		if(roleFilter.getEnabled() != null) {
			especificaciones.add(roleSpecifications.findByEnabled(roleFilter.getEnabled()));
		}
		
		if( (!order.equals("null"))  && (order != null)) {
			pageable = PageRequest.of(pageNumber, pagesize, Sort.by(order));
		}
		
		else {
			pageable = PageRequest.of(pageNumber, pagesize);
		}
		
		if(especificaciones.isEmpty()) {
			return roleRepository.findAll(pageable) ;
		}
		
		return roleRepository.findAll(build(especificaciones),pageable) ;
	}

	private Specification<Role> build(List<Specification<Role>> specs) {
        
        Specification<Role> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
        	result = Specification.where(result).and(specs.get(i));
        }       
        return result;
    }
	
	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}
}
