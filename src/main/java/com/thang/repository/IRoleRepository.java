package com.thang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thang.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
	
	
}
