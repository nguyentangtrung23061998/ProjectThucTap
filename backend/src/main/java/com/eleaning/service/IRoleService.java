package com.eleaning.service;

import java.util.List;

import com.eleaning.entity.RoleEntity;

public interface IRoleService {
	public RoleEntity findByRolename(String rolename);
	public List<RoleEntity> getRoles();
}
