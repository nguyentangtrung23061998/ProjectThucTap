package com.eleaning.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="role")
public class RoleEntity {
	@Id
	private Long id;
	
	@Column(name="rolename")
	@NaturalId
	private String rolename;
	
	@Column(name="createddate")
	private Timestamp createddate;
	
	@Column(name="modifieddate")
	private Timestamp modifieddate;
	
	@Column(name="createdby")
	private String createdby;
	
	@Column(name="modifiedby")
	private String modifiedby;
	
	@ManyToMany(mappedBy = "role",fetch = FetchType.EAGER)
	private Set<UserEntity> users = new HashSet<UserEntity>();
	

	public RoleEntity() {
	}

	public RoleEntity(Long id, String rolename, Timestamp createddate,
			Timestamp modifieddate, String createdby, String modifiedby) {
		this.id = id;
		this.rolename = rolename;
		this.createddate = createddate;
		this.modifieddate = modifieddate;
		this.createdby = createdby;
		this.modifiedby = modifiedby;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the rolename
	 */
	public String getRolename() {
		return rolename;
	}

	/**
	 * @param rolename the rolename to set
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	/**
	 * @return the createddate
	 */
	public Timestamp getCreateddate() {
		return createddate;
	}

	/**
	 * @param createddate the createddate to set
	 */
	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	/**
	 * @return the modifieddate
	 */
	public Timestamp getModifieddate() {
		return modifieddate;
	}

	/**
	 * @param modifieddate the modifieddate to set
	 */
	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}

	/**
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the modifiedby
	 */
	public String getModifiedby() {
		return modifiedby;
	}

	/**
	 * @param modifiedby the modifiedby to set
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	/**
	 * @return the users
	 */
	public Set<UserEntity> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<UserEntity> users) {
		this.users = users;
	}

}