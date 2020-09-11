package com.eleaning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.eleaning.entity.QuestionsBankEntity;

public interface IQuestionsBankRepository extends CrudRepository<QuestionsBankEntity, Long>{
	@Query("SELECT m FROM QuestionsBankEntity m WHERE lecture.id = :lectureid ")
	Optional<QuestionsBankEntity> findByLectureId(@Param("lectureid") Long lectureid);
	
	@Query("SELECT m FROM QuestionsBankEntity m WHERE lecture.id = :lectureid ")
	List<QuestionsBankEntity> findByLectureIdAll(@Param("lectureid") Long lectureid);
}
