package com.suo.sdemo.common.enmus;
/**
 * 系统角色
 * 系统内部所有角色相关内容必须使用该类
 * 系统尽量弱化角色code使用，不推荐系统内直接使用role_code进行判断
 * 超级管理员为唯一系统固化角色，可以使用，但尽量少用；
 * @author 锁战强
 *
 */
public enum RoleEnum {
	ROLE_ROOT("超级管理员"),  //超级管理员
	ROLE_COMMON_USER("一般用户"),   //一般用户
	;
    
    /**
     * 
     */
    private RoleEnum(String desc) {
        this.desc = desc;
    }
    
	public String getDesc() {
        return desc;
    }
   

    private String desc;
	
	
	public static final String CODE_ROLE_ROOT = "ROLE_ROOT"; //用于shiro注解
	
	
	
}
