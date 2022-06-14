package com.suo.sdemo.common.enmus;
/**
 * 系统资源
 * 系统资源为系统固化内容，推荐用来进行权限判断
 * 所有系统资源相关内容，必须使用该类
 * @author 锁战强
 *
 */
public enum SysResourceEnum {
	
	//数据范围
	RES_DATA_USER_ALL("所有用户",3,SysResourceEnum.CODE_RES_DATA_USER_ALL),
	
	;
	
	/**
	 * 权限范围
	 */
	public static final String CODE_RES_DATA_USER_ALL = "RES_DATA_USER_ALL";
	
	private String name;//资源名称
	private Integer type;//资源类别 1-菜单，2-按钮，3-数据范围
	private String code;
	
	private SysResourceEnum(String name, Integer type,String code) {
		this.setName(name);
		this.setType(type);
		this.code = code;
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	protected void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
