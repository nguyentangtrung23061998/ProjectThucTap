package com.eleaning.bean;

import javax.persistence.Id;

public class MediaBean {
	@Id
	private Long id;
	
	private String name;
	
	private String type;
	
	private byte[] mediaByte;

	public MediaBean(Long id, String name, String type, byte[] mediaByte) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.mediaByte = mediaByte;
	}

	public MediaBean() {
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

	/**
	 * @return the mediaByte
	 */
	public byte[] getMediaByte() {
		return mediaByte;
	}

	/**
	 * @param mediaByte the mediaByte to set
	 */
	public void setMediaByte(byte[] mediaByte) {
		this.mediaByte = mediaByte;
	}
}
