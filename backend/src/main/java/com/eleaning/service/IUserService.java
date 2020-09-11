package com.eleaning.service;

import java.util.List;

import com.eleaning.entity.UserEntity;

public interface IUserService {
	public UserEntity findUser(String username);
	public UserEntity save(UserEntity entity);
	public Boolean existsByUsername(String username);
	public Boolean existsByEmail(String email);
	public UserEntity findUserByid(long id);
	public List<UserEntity> getUsers();
	public void delete(Long id);
	public UserEntity findByToken(String token);
	public UserEntity findByEmail(String email);
	public List<UserEntity> getUsersByStudent();
	public List<UserEntity> getUsersByAdmin();
	public List<UserEntity> getUsersByTeacher();
}
