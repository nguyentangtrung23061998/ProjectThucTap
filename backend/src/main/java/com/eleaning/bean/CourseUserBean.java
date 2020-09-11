package com.eleaning.bean;

public class CourseUserBean {
	private CourseBean course;
	private UserAboutCourseBean teacher;
	
	public CourseUserBean(){}

	/**
	 * @return the course
	 */
	public CourseBean getCourse() {
		return course;
	}
	

	public CourseUserBean(CourseBean course, UserAboutCourseBean teacher) {
		this.course = course;
		this.teacher = teacher;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(CourseBean course) {
		this.course = course;
	}

	/**
	 * @return the teacher
	 */
	public UserAboutCourseBean getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(UserAboutCourseBean teacher) {
		this.teacher = teacher;
	}

	
	
}
