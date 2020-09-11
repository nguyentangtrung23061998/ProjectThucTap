package com.eleaning.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleaning.entity.CourseEntity;
import com.eleaning.entity.RoleEntity;
import com.eleaning.entity.UserEntity;
import com.eleaning.repository.IUserRepository;
import com.eleaning.service.IUserService;
import com.eleaning.util.Constant;
import com.eleaning.util.Util;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserEntity save(UserEntity entity) {
		if (entity.getId() == 0) {
			entity.setCreateddate(new Timestamp(System.currentTimeMillis()));

		} else {
			entity.setModifieddate(new Timestamp(System.currentTimeMillis()));
		}
		userRepository.save(entity);
		return entity;
	}

	@Override
	public UserEntity findUser(String username) {
		try {
			Optional<UserEntity> data = userRepository.findByUsername(username);
			if (data.isPresent()) {
				return data.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public UserEntity findUserByid(long id) {
		try {
			Optional<UserEntity> data = userRepository.findById(id);
			if (data.isPresent()) {
				return data.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UserEntity> getUsers() {
		try {
			System.out.println("aaaaaaa");
			List<UserEntity> result = new ArrayList<UserEntity>();
			userRepository.findAll().forEach(result::add);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<UserEntity>();
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserEntity findByToken(String token) {
		try {
			Optional<UserEntity> data = userRepository.findByToken(token);
			if (data.isPresent()) {
				return data.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserEntity findByEmail(String email) {
		try {
			System.out.println("email: " + email);
			Optional<UserEntity> data = userRepository.findByEmail(email);
			if (data.isPresent()) {
				return data.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UserEntity> getUsersByStudent() {
		try {
			List<UserEntity> users = new ArrayList<UserEntity>();
			userRepository.findAll().forEach(users::add);
			List<UserEntity> listData = new ArrayList<UserEntity>();
			System.out.println("users: " + users);
			for (UserEntity userEntity : users) {
				Iterator<RoleEntity> roles = userEntity.getRole().iterator();
				String role = "";
				while (roles.hasNext()) {
					role = roles.next().getRolename();
					break;
				}
				if (role.equals(Constant.role[2])) {
					listData.add(userEntity);
				}
			}
			return listData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<UserEntity>();
	}

	@Override
	public List<UserEntity> getUsersByAdmin() {
		try {
			List<UserEntity> users = new ArrayList<UserEntity>();
			userRepository.findAll().forEach(users::add);
			List<UserEntity> listData = new ArrayList<UserEntity>();
			for (UserEntity userEntity : users) {
				Iterator<RoleEntity> roles = userEntity.getRole().iterator();
				String role = "";
				while (roles.hasNext()) {
					role = roles.next().getRolename();
					break;
				}
				if (role.equals(Constant.role[0])) {
					listData.add(userEntity);
				}
			}
			return listData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<UserEntity>();
	}

	@Override
	public List<UserEntity> getUsersByTeacher() {
		try {
			List<UserEntity> users = new ArrayList<UserEntity>();
			userRepository.findAll().forEach(users::add);
			List<UserEntity> listData = new ArrayList<UserEntity>();
			for (UserEntity userEntity : users) {
				Iterator<RoleEntity> roles = userEntity.getRole().iterator();
				String role = "";
				while (roles.hasNext()) {
					role = roles.next().getRolename();
					break;
				}
				if (role.equals(Constant.role[1])) {
					listData.add(userEntity);
				}
			}
			return listData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<UserEntity>();
	}
}