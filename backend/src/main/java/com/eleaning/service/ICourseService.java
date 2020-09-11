package com.eleaning.service;

import java.util.List;

import com.eleaning.entity.CourseEntity;

public interface ICourseService {
	public CourseEntity save(CourseEntity course);
	public List<CourseEntity> getAll();
	public boolean delete(Long id);
	public CourseEntity findById(Long id);
	public List<CourseEntity> getCoursesByUserId(Long userId);
}
