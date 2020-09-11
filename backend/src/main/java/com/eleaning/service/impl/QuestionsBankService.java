package com.eleaning.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleaning.entity.QuestionsBankEntity;
import com.eleaning.repository.IQuestionsBankRepository;
import com.eleaning.service.IQuestionsBankService;

@Service
public class QuestionsBankService implements IQuestionsBankService {

	@Autowired
	private IQuestionsBankRepository questionsBankRepository;

	@Override
	public QuestionsBankEntity save(QuestionsBankEntity questionsBankEntity) {
		try {
			if (questionsBankEntity.getId() == null) {
				questionsBankEntity.setId(Calendar.getInstance().getTimeInMillis());
				questionsBankEntity.setCreateddate(new Timestamp(System.currentTimeMillis()));
			} else {
				questionsBankEntity.setModifieddate(new Timestamp(System.currentTimeMillis()));
			}
			questionsBankRepository.save(questionsBankEntity);
			return questionsBankEntity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<QuestionsBankEntity> getAll() {
		try {
			List<QuestionsBankEntity> result = new ArrayList<QuestionsBankEntity>();
			questionsBankRepository.findAll().forEach(result::add);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<QuestionsBankEntity>();
	}

	@Override
	public boolean delete(Long id) {
		try {
			questionsBankRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public QuestionsBankEntity findById(Long id) {
		try {
			Optional<QuestionsBankEntity> data = questionsBankRepository.findById(id);
			if(data.isPresent()) {
				return data.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QuestionsBankEntity();
	}

	@Override
	public List<QuestionsBankEntity> getQuestionBankByLecture(Long id) {
		try {
			List<QuestionsBankEntity> listData = questionsBankRepository.findByLectureIdAll(id);
			return listData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
