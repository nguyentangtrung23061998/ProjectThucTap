package com.eleaning.bean;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpBean {
	@NotBlank
	@Size(min = 3, max = 50)
	private String username;

	@NotBlank
	@Size(min = 3, max = 50)
	private String password;

	@NotBlank
    @Size(max = 60)
    @Email
	private String email;
	
	@Size(min=3, max = 50)
	private String fullname;

	private Set<String> role;
	
	public SignUpBean() {
	}

	public SignUpBean(@NotBlank @Size(min = 3, max = 50) String username,
			@NotBlank @Size(min = 3, max = 50) String password, @NotBlank @Size(max = 60) @Email String email,
			@Size(min = 3, max = 50) String fullname, Set<String> role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullname = fullname;
		this.role = role;
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
	 * @return the role
	 */
	public Set<String> getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Set<String> role) {
		this.role = role;
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
	
}
