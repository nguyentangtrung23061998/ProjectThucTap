package com.eleaning.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="lecture")
public class LectureEntity {
	@Id
	private Long id;
	
	@Size(min=3, max = 50)
	private String name;
	
	private String description;
	
	private String image;
	private String video;
	private String audio;
	private String document;
	
	private Timestamp createddate;
	
	private Timestamp modifieddate;
	
	private String createdby;
	
	private String modifiedby;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseid", referencedColumnName = "id")
	@JsonIgnore
    private CourseEntity course;
	
//	@OneToMany(
//    mappedBy = "course_ex")
//@JsonIgnore
//private List<ExamCourseEntity> examcourses;
	
	@OneToMany(mappedBy = "lecture")
	@JsonIgnore
	private List<QuestionsBankEntity> questionsBanks;

	public LectureEntity(Long id, @Size(min = 3, max = 50) String name, String description, String image, String video,
			String audio, String document, Timestamp createddate, Timestamp modifieddate, String createdby,
			String modifiedby, CourseEntity course) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.video = video;
		this.audio = audio;
		this.document = document;
		this.createddate = createddate;
		this.modifieddate = modifieddate;
		this.createdby = createdby;
		this.modifiedby = modifiedby;
		this.course = course;
	}

	public LectureEntity() {
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
	 * @return the video
	 */
	public String getVideo() {
		return video;
	}

	/**
	 * @param video the video to set
	 */
	public void setVideo(String video) {
		this.video = video;
	}

	/**
	 * @return the audio
	 */
	public String getAudio() {
		return audio;
	}

	/**
	 * @param audio the audio to set
	 */
	public void setAudio(String audio) {
		this.audio = audio;
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
	 * @return the document
	 */
	public String getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(String document) {
		this.document = document;
	}

	/**
	 * @return the course
	 */
	public CourseEntity getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(CourseEntity course) {
		this.course = course;
	}

	/**
	 * @return the description
	 */
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
	 * @return the questionsBanks
	 */
	public List<QuestionsBankEntity> getQuestionsBanks() {
		return questionsBanks;
	}

	/**
	 * @param questionsBanks the questionsBanks to set
	 */
	public void setQuestionsBanks(List<QuestionsBankEntity> questionsBanks) {
		this.questionsBanks = questionsBanks;
	}
}