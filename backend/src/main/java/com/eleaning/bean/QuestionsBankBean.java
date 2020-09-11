package com.eleaning.bean;

public class QuestionsBankBean {
	
	private Long id;
	
	private String question;

	private String answerfirst;

	private String answersecond;

	private String answerthird;

	private String answerfourth;

	private String correctanswer;
	
	private Long lectureId;


	public QuestionsBankBean(Long id, String question, String answerfirst, String answersecond, String answerthird,
			String answerfourth, String correctanswer, Long lectureId) {
		this.id = id;
		this.question = question;
		this.answerfirst = answerfirst;
		this.answersecond = answersecond;
		this.answerthird = answerthird;
		this.answerfourth = answerfourth;
		this.correctanswer = correctanswer;
		this.lectureId = lectureId;
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



	public QuestionsBankBean() {
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
	 * @return the lectureId
	 */
	public Long getLectureId() {
		return lectureId;
	}

	/**
	 * @param lectureId the lectureId to set
	 */
	public void setLectureId(Long lectureId) {
		this.lectureId = lectureId;
	}
}
