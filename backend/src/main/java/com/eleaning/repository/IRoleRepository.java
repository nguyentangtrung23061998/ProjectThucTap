package com.eleaning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.eleaning.entity.RoleEntity;


public interface IRoleRepository extends CrudRepository<RoleEntity, Long>{
	@Query("SELECT m FROM RoleEntity m WHERE rolename = :rolename ")
	Optional<RoleEntity> findByUsername(@Param("rolename") String rolename);
}
