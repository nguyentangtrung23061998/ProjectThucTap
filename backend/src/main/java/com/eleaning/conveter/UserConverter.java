package com.eleaning.conveter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eleaning.bean.UserAboutCourseBean;
import com.eleaning.bean.UserBean;
import com.eleaning.entity.UserEntity;

@Component
public class UserConverter {
	public UserBean convertBean(UserEntity userEntity) {
		ModelMapper modelMapper = new ModelMapper();
		UserBean result =modelMapper.map(userEntity, UserBean.class);
		return result;
	}
	
	public UserAboutCourseBean convertUserAboutBean(UserEntity userEntity) {
		ModelMapper modelMapper = new ModelMapper();
		UserAboutCourseBean result =modelMapper.map(userEntity, UserAboutCourseBean.class);
		return result;
	}
	
	public UserEntity convertEntity(UserBean userBean) {
		ModelMapper modelMapper = new ModelMapper();
		UserEntity result = modelMapper.map(userBean, UserEntity.class);
		return result;
	}
}
