package com.eleaning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.eleaning.entity.CourseEntity;
import com.eleaning.entity.MediaEntity;

public interface IMediaRepository extends JpaRepository<MediaEntity, Long> {
	Optional<MediaEntity> findByName(String name);
}
