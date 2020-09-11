package com.eleaning.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="questions_bank")
public class QuestionsBankEntity {
	@Id
	private Long id;
	
	private String question;
	
	private String answerfirst;
	
	private String answersecond;
	
	private String answerthird;
	
	private String answerfourth;
	
	private String correctanswer;
	
	private Timestamp createddate;

	private Timestamp modifieddate;

	private String createdby;

	private String modifiedby;

//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "courseid", referencedColumnName = "id")
//	@JsonIgnore
//    private CourseEntity course_ex;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lectureid", referencedColumnName = "id")
	@JsonIgnore
	private LectureEntity lecture;
	
	public QuestionsBankEntity(Long id, String question, String answerfirst, String answersecond, String answerthird,
			String answerfourth, String correctanswer, Timestamp createddate, Timestamp modifieddate, String createdby,
			String modifiedby) {
		this.id = id;
		this.question = question;
		this.answerfirst = answerfirst;
		this.answersecond = answersecond;
		this.answerthird = answerthird;
		this.answerfourth = answerfourth;
		this.correctanswer = correctanswer;
		this.createddate = createddate;
		this.modifieddate = modifieddate;
		this.createdby = createdby;
		this.modifiedby = modifiedby;
	}

	public QuestionsBankEntity() {
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
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the answerfirst
	 */
	public String getAnswerfirst() {
		return answerfirst;
	}

	/**
	 * @param answerfirst the answerfirst to set
	 */
	public void setAnswerfirst(String answerfirst) {
		this.answerfirst = answerfirst;
	}

	/**
	 * @return the answersecond
	 */
	public String getAnswersecond() {
		return answersecond;
	}

	/**
	 * @param answersecond the answersecond to set
	 */
	public void setAnswersecond(String answersecond) {
		this.answersecond = answersecond;
	}

	/**
	 * @return the answerthird
	 */
	public String getAnswerthird() {
		return answerthird;
	}

	/**
	 * @param answerthird the answerthird to set
	 */
	public void setAnswerthird(String answerthird) {
		this.answerthird = answerthird;
	}

	/**
	 * @return the answerfourth
	 */
	public String getAnswerfourth() {
		return answerfourth;
	}

	/**
	 * @param answerfourth the answerfourth to set
	 */
	public void setAnswerfourth(String answerfourth) {
		this.answerfourth = answerfourth;
	}

	/**
	 * @return the correctanswer
	 */
	public String getCorrectanswer() {
		return correctanswer;
	}

	/**
	 * @param correctanswer the correctanswer to set
	 */
	public void setCorrectanswer(String correctanswer) {
		this.correctanswer = correctanswer;
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
	 * @return the lecture
	 */
	public LectureEntity getLecture() {
		return lecture;
	}

	/**
	 * @param lecture the lecture to set
	 */
	public void setLecture(LectureEntity lecture) {
		this.lecture = lecture;
	}
}