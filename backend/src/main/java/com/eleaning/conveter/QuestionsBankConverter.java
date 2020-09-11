package com.eleaning.conveter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eleaning.bean.QuestionsBankBean;
import com.eleaning.entity.QuestionsBankEntity;

@Component
public class QuestionsBankConverter {
	public QuestionsBankBean convertBean(QuestionsBankEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		QuestionsBankBean result =modelMapper.map(entity, QuestionsBankBean.class);
		return result;
	}
	
	public QuestionsBankEntity convertEntity(QuestionsBankBean bean) {
		ModelMapper modelMapper = new ModelMapper();
		QuestionsBankEntity result = modelMapper.map(bean, QuestionsBankEntity.class);
		return result;
	}
}
