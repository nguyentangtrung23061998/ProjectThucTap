package com.eleaning.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.eleaning.entity.CourseEntity;

public interface ICourseRepository extends CrudRepository<CourseEntity, Long> {

	@Query("SELECT m FROM CourseEntity m WHERE user.id = :userid ")
	List<CourseEntity> getAlByCourseId(@Param("userid") Long userid);

//	@Modifying
//	@Query("delete from CourseEntity where id =: id")
//	void deleteCourseById(@Param("id") Long id);

	@Modifying
	@Transactional
	@Query("delete from CourseEntity e where id = ?1")
	void deleteCourseById(Long id);
}
