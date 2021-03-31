package com.thang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thang.model.Role;
import com.thang.repository.IRoleRepository;

@Service
public class RoleService implements IRoleService{
	
	@Autowired
	private IRoleRepository roleRepository;
	
	public List<Role> findAll(){
		return roleRepository.findAll();
	}
}
