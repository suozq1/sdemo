package com.suo.sdemo.buss.sys.pojo.form;

import javax.validation.constraints.NotNull;

import com.suo.sdemo.common.ErrorCode;

public class UserInfoForm {
	
	@NotNull(message = ErrorCode.CODE_PARAM_NULL)
	private Integer userId;
	
	private String userName;
	
	private String password;
	
	private Integer gender;
	
	private String phone;
	
	private String email;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
