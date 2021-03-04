package com.onenpro.o2021.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface O2021Repository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>{

}
