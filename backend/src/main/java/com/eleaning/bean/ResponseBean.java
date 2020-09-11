package com.eleaning.bean;

import org.springframework.http.HttpStatus;

/*
HTTP has a few important verbs.

    POST - Create a new resource
    GET - Read a resource
    PUT - Update an existing resource
    DELETE - Delete a resource

HTTP also defines standard response codes.

    200 - SUCESS
    404 - RESOURCE NOT FOUND
    400 - BAD REQUEST
    201 - CREATED
    401 - UNAUTHORIZED
    415 - UNSUPPORTED TYPE - Representation not supported for the resource
    500 - SERVER ERROR

 */
public class ResponseBean {
	private String messageKey = null;
	private String message = null;

	private int status;
	private Object data;

	public ResponseBean() {
		super();
	}

	public ResponseBean(String messageKey, String message, int status, Object data) {
		super();
		this.messageKey = messageKey;
		this.message = message;
		this.status = status;
		this.data = data;
	}

	public void setMessages(String messageKey, String message) {
		this.messageKey = messageKey;
		this.message = message;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public void setCouseIdNotFound() {
		this.setMessages("msg.notFound", "Course is not found");
		this.setStatus(HttpStatus.NOT_FOUND.value());
	}

	public void setNotFound() {
		this.setMessages("msg.notFound", "Not found");
		this.setStatus(HttpStatus.NOT_FOUND.value());
	}

	public void setWrongFormatDate() {
		this.setMessages("msg.wrongFormatDate", "Wrong format date");
		this.setStatus(HttpStatus.BAD_REQUEST.value());
	}
	
	public void setRoleUserNotFound() {
		this.setMessages("msg.notFoundRoleUser", "Not found role user");
		this.setStatus(HttpStatus.BAD_REQUEST.value());
	}

	public void setIdObjectNotFound() {
		this.setMessages("msg.notFoundRoleUser", "Not found object");
		this.setStatus(HttpStatus.BAD_REQUEST.value());
	}
	
	public void setBadRequest() {
		this.setMessages("msg.badRequest", "Bad Request");
		this.setStatus(HttpStatus.BAD_REQUEST.value());
	}

	public void setEnterAllRequiredFields() {
		this.setMessages("msg.pleaseEnterAllRequiredFields", "Please enter all required fields");
		this.setStatus(HttpStatus.BAD_REQUEST.value());
	}

	public void setSuccess() {
		this.setMessages("msg.success", "Success");
		this.setStatus(HttpStatus.OK.value());
	}

	public void setInsertSuccess() {
		this.setMessages("msg.success", "Success");
		this.setStatus(HttpStatus.CREATED.value());
	}

	public void setFailed(String messageKey, String message) {
		this.setMessages(messageKey, message);
		this.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
	}
	
	public void setFailUpload() {
		this.setMessages("msg.failupload", "Fail upload");
		this.setStatus(HttpStatus.BAD_REQUEST.value());
	}

	public void setNotAllowed() {
		this.setMessages("msg.youAreNotAllowed", "You are not allowed");
		this.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
	}

	public void setInternalServerError() {
		this.setMessages("msg.internalServerError", "Internal Server Error");
		this.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	public void setIsExisting() {
		this.setMessages("msg.isExisting", "Username is existing");
		this.setStatus(HttpStatus.FOUND.value());
	}

	public void setEmailIsExisting() {
		this.setMessages("msg.emailIsExisting", "Email is existing");
		this.setStatus(HttpStatus.FOUND.value());
	}

	public void setInvalidEmailAddress() {
		this.setMessages("msg.emailFormatIsInvalid", "Email format is invalid");
		this.setStatus(HttpStatus.FOUND.value());
	}

	public void setInvalid() {
		this.setMessages("msg.invalid", "Invalid");
		this.setStatus(HttpStatus.FOUND.value());
	}

	public void setInvalidAccount() {
		this.setMessages("msg.invalidAccount", "Invalid account");
		this.setStatus(HttpStatus.FOUND.value());
	}

	public void setInvalidToken() {
		this.setMessages("msg.invalidToken", "Invalid token");
		this.setStatus(HttpStatus.FOUND.value());
	}

	public void setIsBeingUsed() {
		this.setMessages("msg.thiIsBeingUsed", "This is being used");
		this.setStatus(HttpStatus.FOUND.value());
	}

	public void setRoleFail() {
		this.setMessages("msg.roleFail", "Role is not suitable");
		this.setStatus(HttpStatus.BAD_REQUEST.value());
		
	}
	public void setDeleteFail() {
		this.setMessages("msg.deleteFail", "Delete fail");
		this.setStatus(HttpStatus.BAD_REQUEST.value());
		
	}
	
	public void setLoginFail() {
		this.setMessages("msg.loginFail", "Login fail");
		this.setStatus(HttpStatus.BAD_REQUEST.value());
		
	}
	
	public void setPasswordSame() {
		this.setMessages("msg.passwordInvalid","The password isn't the same");
		this.setStatus(HttpStatus.BAD_REQUEST.value());
	}

	public void setPasswordFail() {
		this.setMessages("msg.passwordInvalid","The password is invalid");
		this.setStatus(HttpStatus.BAD_REQUEST.value());
	}

//	public void setTokenTimeout() {
//		this.setMessages("msg.timeOut", "Time out");
//		this.setStatus(Constant.TIME_OUT_CODE);
//	}
}
