package com.eleaning.bean;

public class ChangePassword {
	private String newPassword;
	private String currentPassword;
	
	public ChangePassword(String newPassword, String currentPassword) {
		this.newPassword = newPassword;
		this.currentPassword = currentPassword;
	}

	public ChangePassword() {
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}

	/**
	 * @param currentPassword the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	
}
