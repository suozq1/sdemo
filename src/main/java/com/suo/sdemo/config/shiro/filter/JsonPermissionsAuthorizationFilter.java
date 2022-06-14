package com.suo.sdemo.config.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import com.suo.sdemo.common.ErrorCode;
import com.suo.sdemo.util.WebUtils;
/**
 * 重写权限过滤器的onAccessDenied方法，避免跳转登录页
 * @author 锁战强
 *
 */
public class JsonPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            WebUtils.writeErrorCode(request, response, ErrorCode.TOKEN_NOT_FOUND);
        } else {
            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
            WebUtils.writeErrorCode(request, response, ErrorCode.UNAUTHORIZED);
        }
        return false;
    }
	
	
	
}
