package com.eleaning.bean;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.Size;

import com.eleaning.entity.LectureEntity;

public class CourseBean {

	private Long id;

	@Size(min = 3, max = 50)
	private String name;

	private String image;

	private String description;

	private boolean isActive;

	private Integer totalStudentEnroll = 0;
	private Timestamp createddate;

	private Timestamp modifieddate;

	private List<LectureEntity> letures;

	public CourseBean(Long id, @Size(min = 3, max = 50) String name, String image, String description, boolean isActive,
			Integer totalStudentEnroll, List<LectureEntity> letures) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.description = description;
		this.isActive = isActive;
		this.totalStudentEnroll = totalStudentEnroll;
		this.letures = letures;
	}

	public CourseBean() {
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

	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the letures
	 */
	public List<LectureEntity> getLetures() {
		return letures;
	}

	/**
	 * @param letures the letures to set
	 */
	public void setLetures(List<LectureEntity> letures) {
		this.letures = letures;
	}

	/**
	 * @return the totalStudentEnroll
	 */
	public Integer getTotalStudentEnroll() {
		return totalStudentEnroll;
	}

	/**
	 * @param totalStudentEnroll the totalStudentEnroll to set
	 */
	public void setTotalStudentEnroll(Integer totalStudentEnroll) {
		this.totalStudentEnroll = totalStudentEnroll;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public Timestamp getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}
}
