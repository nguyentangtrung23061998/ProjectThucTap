package com.eleaning.bean;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

public class UserBean {
	private Long id;
	private String username;
	private String password;
	private String email;
	private String image;
	private String fullname;
	private String role;
	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProviderBean provider;

	public UserBean(Long id, String username, String password, String email, String image, String fullname,
			String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.image = image;
		this.fullname = fullname;
		this.role = role;
	}

	public UserBean() {
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	public AuthProviderBean getProvider() {
		return provider;
	}

	public void setProvider(AuthProviderBean provider) {
		this.provider = provider;
	}
}
