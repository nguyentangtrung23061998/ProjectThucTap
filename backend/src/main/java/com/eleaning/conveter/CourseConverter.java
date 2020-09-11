package com.eleaning.conveter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eleaning.bean.CourseBean;
import com.eleaning.entity.CourseEntity;

@Component
public class CourseConverter {
	public CourseBean convertBean(CourseEntity courseEntity) {
		ModelMapper modelMapper = new ModelMapper();
		CourseBean result =modelMapper.map(courseEntity, CourseBean.class);
		return result;
	}
	
	public CourseEntity convertEntity(CourseBean courseBean) {
		ModelMapper modelMapper = new ModelMapper();
		CourseEntity result = modelMapper.map(courseBean, CourseEntity.class);
		return result;
	}
}
