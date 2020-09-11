package com.eleaning.service;

import java.util.List;

import com.eleaning.entity.LectureEntity;

public interface ILectureService {
	public List<LectureEntity> getAll();
	public List<LectureEntity> getLectureByCourse(Long courseId);
	public LectureEntity findById(Long id);
	public LectureEntity save(LectureEntity entity);
	public boolean delete(Long id);
}
