package com.suo.sdemo.util;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.suo.sdemo.buss.sys.entity.SysUser;


/**
 * APP
 * 和应用整体相关的Util类，目前主要是权限相关内容
 * @author 锁战强
 *
 */
@Component
public class AppUtils {
    
	public static final String USER_ROOT= "root";
	/**
	 * 表字段值列表，全局
	 */
	public static final Integer DEL_TRUE = 1; //删除
	public static final Integer DEL_FALSE = 0; //未删除
	
	public static final Integer DEFAULT_USER_ID = 0; //默认用户ID,代码系统本身，凡是由系统创建的数据，创建人ID为0；
	
	private static String ENV;//当前环境：dev或者pro
	
	
	/**
	 * 检测当前用户是否有对应权限
	 * @param permission
	 * @return
	 */
	public static boolean isPermitted(String resourceCode) {
		return SecurityUtils.getSubject().isPermitted(resourceCode);
	}
	
	/**
	 * 检查当前用户是否有对应角色
	 * @param roleCode
	 * @return
	 */
	public static boolean hasRole(String roleCode) {
		return SecurityUtils.getSubject().hasRole(roleCode);
	}
	
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public static SysUser getCurrentUser() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}
	
	public static Integer getCurrentUserId() {
	    return getCurrentUser().getUserId();
	}
	
	private AppUtils() {}
	

    public static String getENV() {
        return ENV;
    }

    @Value("${spring.profiles.active}")
    public void setENV(String env) {
        ENV = env;
    }
    
    /**
     * 判断当前环境
     * @return
     */
    public static boolean isProd() {
        return "pro".equals(ENV);
    }
}
