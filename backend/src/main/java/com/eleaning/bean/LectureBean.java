package com.eleaning.bean;

import javax.validation.constraints.Size;

public class LectureBean {
	
	private Long id;
	
	@Size(min=3, max = 50)
	private String name;
	
	private String description;
	
	private String image;
	private String video;
	private String audio;
	private String document;
	
	public LectureBean(Long id, @Size(min = 3, max = 50) String name, String description, String image, String video,
			String audio, String document) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.video = video;
		this.audio = audio;
		this.document = document;
	}


	public LectureBean() {
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

	
	
}
