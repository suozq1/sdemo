package com.suo.sdemo.buss.sys.pojo.form;

import javax.validation.constraints.NotBlank;

import com.suo.sdemo.common.ErrorCode;
import com.suo.sdemo.common.validgroup.ValidGroup;

public class SignForm {
	@NotBlank(message = ErrorCode.CODE_PARAM_NULL,groups = {ValidGroup.Login.class,ValidGroup.SignUp.class})
	private String email;
	@NotBlank(message = ErrorCode.CODE_PARAM_NULL,groups = {ValidGroup.Login.class,ValidGroup.SignUp.class})
	private String password;
	@NotBlank(message = ErrorCode.CODE_PARAM_NULL,groups = {ValidGroup.Login.class,ValidGroup.SignUp.class})
	private String captcha;
	@NotBlank(message = ErrorCode.CODE_PARAM_NULL,groups = {ValidGroup.Login.class,ValidGroup.SignUp.class})
	private String ckey;
	@NotBlank(message = ErrorCode.CODE_PARAM_NULL,groups = {ValidGroup.SignUp.class})
	private String nickname;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getCkey() {
		return ckey;
	}
	public void setCkey(String ckey) {
		this.ckey = ckey;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
