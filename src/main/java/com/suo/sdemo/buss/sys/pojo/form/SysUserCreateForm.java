package com.suo.sdemo.buss.sys.pojo.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.suo.sdemo.buss.sys.entity.SysUser;
import com.suo.sdemo.common.ErrorCode;

public class SysUserCreateForm extends SysUser{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5247320938656340261L;

	@NotBlank(message = ErrorCode.CODE_PARAM_NULL)
	private String account;
	
	@NotBlank(message = ErrorCode.CODE_PARAM_NULL)
	private String userName;
	
	private int gender;
	
	private Integer status=1;
	
	@NotNull(message = ErrorCode.CODE_PARAM_NULL)
	private Integer orgId;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
}
