package com.eleaning.conveter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eleaning.bean.LectureBean;
import com.eleaning.entity.LectureEntity;

@Component
public class LectureConverter {
	public LectureBean convertBean(LectureEntity lectureEntity) {
		ModelMapper modelMapper = new ModelMapper();
		LectureBean result =modelMapper.map(lectureEntity, LectureBean.class);
		return result;
	}
	
	public LectureEntity convertEntity(LectureBean lectureBean) {
		ModelMapper modelMapper = new ModelMapper();
		LectureEntity result = modelMapper.map(lectureBean, LectureEntity.class);
		return result;
	}
}
