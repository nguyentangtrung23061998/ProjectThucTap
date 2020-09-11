package com.eleaning.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="media")
public class MediaEntity {
	@Id
	private Long id;
	
	private String name;
	
	private String type;
	
//	@Column(name = "picByte", length = 1000)
//	private byte[] picByte;
	
	public MediaEntity() {}

	public MediaEntity(Long id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
