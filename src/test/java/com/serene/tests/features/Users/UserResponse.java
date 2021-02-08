package com.serene.tests.features.Users;

public class UserResponse {
	private Integer code;
	private String type;
	private String message;

	/**
	* No args constructor for use in serialization
	 * @return 
	*
	*/
	public UserResponse() {
	}

	/**
	*
	* @param code
	* @param type
	* @param message
	 * @return 
	*/
	public UserResponse(Integer code, String type, String message) {
	super();
	this.code = code;
	this.type = type;
	this.message = message;
	}

	public Integer getCode() {
	return code;
	}

	public void setCode(Integer code) {
	this.code = code;
	}

	public String getType() {
	return type;
	}

	public void setType(String type) {
	this.type = type;
	}

	public String getMessage() {
	return message;
	}

	public void setMessage(String message) {
	this.message = message;
	}
}