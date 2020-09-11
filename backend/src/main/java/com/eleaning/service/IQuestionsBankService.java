package com.eleaning.service;

import java.util.List;

import com.eleaning.entity.QuestionsBankEntity;

public interface IQuestionsBankService {
	public QuestionsBankEntity save(QuestionsBankEntity examCourse);
	public List<QuestionsBankEntity> getAll();
	public List<QuestionsBankEntity> getQuestionBankByLecture(Long id);
	public boolean delete(Long id);
	public QuestionsBankEntity findById(Long id);
}
