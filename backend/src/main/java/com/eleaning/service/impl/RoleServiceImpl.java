package com.eleaning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleaning.entity.RoleEntity;
import com.eleaning.repository.IRoleRepository;
import com.eleaning.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService{

	@Autowired
	private IRoleRepository roleRepository;
	
	@Override
	public RoleEntity findByRolename(String username) {
		
		try {
			Optional<RoleEntity> data = roleRepository.findByUsername(username);
			if (data.isPresent()) {
				return data.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<RoleEntity> getRoles() {
		List<RoleEntity> roleEntity = new ArrayList<RoleEntity>();
		roleRepository.findAll().forEach(roleEntity::add);
		return roleEntity.stream().collect(Collectors.toList());
	}
	
}
