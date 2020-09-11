package com.eleaning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.eleaning.entity.LectureEntity;

public interface ILectureRepository extends CrudRepository<LectureEntity, Long>{
	@Query("SELECT m FROM LectureEntity m WHERE course.id = :courseid ")
	Optional<LectureEntity> findByCourseId(@Param("courseid") Long courseid);
	
	@Query("SELECT m FROM LectureEntity m WHERE course.id = :courseid ")
	List<LectureEntity> getAlByCourseId(@Param("courseid") Long courseid);
}
