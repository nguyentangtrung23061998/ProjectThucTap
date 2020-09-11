package com.eleaning.bean;

public class LoginBean {
	private String email;
	private String password;
	public LoginBean(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public LoginBean() {
		super();
	}
	/**
	 * @return the username
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param username the username to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
}
