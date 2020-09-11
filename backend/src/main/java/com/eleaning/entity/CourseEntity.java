package com.eleaning.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="course")
public class CourseEntity {
	@Id
	private Long id;
	
	@Size(min=3, max = 50)
	private String name;
	
	private String description;
	
	private String image;
	
	@Column(name="is_Active")
	private boolean isActive;
	
	private Timestamp createddate;
	
	private Timestamp modifieddate;
	
	private String createdby;
	
	private String modifiedby;
	
	@OneToMany(
		        mappedBy = "course",fetch=FetchType.EAGER
		    )
	private List<LectureEntity> lectures;
	
	@ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
	@JsonIgnore
    private UserEntity user;
	
//	@OneToMany(
//	        mappedBy = "course_ex")
//	@JsonIgnore
//	private List<ExamCourseEntity> examcourses;

	@ManyToMany(mappedBy = "courser_enroll",fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<UserEntity> users = new HashSet<UserEntity>();

	public CourseEntity(Long id, @Size(min = 3, max = 50) String name, String description, String image,
			boolean isActive, Timestamp createddate, Timestamp modifieddate,
			String createdby, String modifiedby, List<LectureEntity> lectures, UserEntity user,
			List<QuestionsBankEntity> examcourses) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.isActive = isActive;
		this.createddate = createddate;
		this.modifieddate = modifieddate;
		this.createdby = createdby;
		this.modifiedby = modifiedby;
		this.lectures = lectures;
		this.user = user;
	}

	public CourseEntity() {
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the lectures
	 */
	public List<LectureEntity> getLectures() {
		return lectures;
	}

	/**
	 * @param lectures the lectures to set
	 */
	public void setLectures(List<LectureEntity> lectures) {
		this.lectures = lectures;
	}

	/**
	 * @return the user
	 */
	public UserEntity getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserEntity user) {
		this.user = user;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CourseEntity [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image
				+ ", isActive=" + isActive + ", createddate=" + createddate + ", modifieddate=" + modifieddate
				+ ", createdby=" + createdby + ", modifiedby=" + modifiedby + ", lectures=" + lectures + ", user="
				+ user + "]";
	}

}