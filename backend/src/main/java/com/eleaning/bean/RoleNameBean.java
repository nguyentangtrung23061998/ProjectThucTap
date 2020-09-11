package com.eleaning.bean;

public enum RoleNameBean {
	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_STUDENT("ROLE_STUDENT"),
	ROLE_TEACHER("ROLE_TEACHER");
	
	private String value;
	
	RoleNameBean(String value) {
        this.value = value;
    }

	public String getValue() {
		return value;
	}
}
