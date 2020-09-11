package com.eleaning.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleaning.api.QuestionsBankRestAPI;
import com.eleaning.entity.LectureEntity;
import com.eleaning.entity.QuestionsBankEntity;
import com.eleaning.repository.ILectureRepository;
import com.eleaning.repository.IQuestionsBankRepository;
import com.eleaning.service.ILectureService;
import com.eleaning.service.IQuestionsBankService;

@Service	 	
public class LectureService implements ILectureService{

	@Autowired
	private ILectureRepository lectureRepository;
	
	@Autowired
	private IQuestionsBankService questionsBankService;
	
	@Override
	public List<LectureEntity> getAll() {
		try {
			List<LectureEntity> result = new ArrayList<LectureEntity>();
			lectureRepository.findAll().forEach(result::add);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<LectureEntity>();
	}

	@Override
	public LectureEntity findById(Long id) {
		try {
			Optional<LectureEntity> data = lectureRepository.findById(id);
			if(data.isPresent()) {
				return data.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new LectureEntity();
	}

	@Override
	public LectureEntity save(LectureEntity entity) {
		try {
			if(entity.getId() == null) {
				entity.setId(Calendar.getInstance().getTimeInMillis());
				entity.setCreateddate(new Timestamp(System.currentTimeMillis()));
			}
			else{
				entity.setModifieddate(new Timestamp(System.currentTimeMillis()));
			}
			lectureRepository.save(entity);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delete(Long id) {
		try {
			List<QuestionsBankEntity> questionsBankEntities = questionsBankService.getQuestionBankByLecture(id);
			for(QuestionsBankEntity questionsBankEntity : questionsBankEntities) {
				questionsBankService.delete(questionsBankEntity.getId());
			}
			lectureRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<LectureEntity> getLectureByCourse(Long courseId) {
		try {
			
			List<LectureEntity> result = new ArrayList<LectureEntity>();
			lectureRepository.getAlByCourseId(courseId).forEach(result::add);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<LectureEntity>();
	}

}
