package com.suo.sdemo.config.shiro.filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import com.suo.sdemo.common.ErrorCode;
import com.suo.sdemo.util.WebUtils;
/**
 * 重写权限过滤器onAccessDenied方法，避免跳转登录页面
 * @author 锁战强
 *
 */
public class JsonFormAuthenticationFilter extends FormAuthenticationFilter {
	
	private static final Logger log = LoggerFactory.getLogger(JsonFormAuthenticationFilter.class);
	
	@Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return super.preHandle(request,response);
    }
   

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		    if (isLoginRequest(request, response)) { //是登录请求
	            if (isLoginSubmission(request, response)) {
	                if (log.isTraceEnabled()) {
	                    log.trace("Login submission detected.  Attempting to execute login.");
	                }
	                return executeLogin(request, response);
	            } else {
	                if (log.isTraceEnabled()) {
	                    log.trace("Login page view.");
	                }
	                //allow them to see the login page ;)
	                return true;
	            }
	        } else { //不是登录请求
	            WebUtils.writeErrorCode(request, response, ErrorCode.TOKEN_NOT_FOUND);
	            return false;
	        }
	}
}
