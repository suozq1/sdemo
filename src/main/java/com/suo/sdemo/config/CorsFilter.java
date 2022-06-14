package com.suo.sdemo.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * 跨域请求配置
 * @author 锁战强
 *
 */
@Component
@ConfigurationProperties("cors")
public class CorsFilter implements Filter {
	
	//允许那些地址访问
	private String allowOrigin = "*";
	
	//是否允许携带cookie，当为true时allowOrigin不能是*；
	private String allowCredentials="false"; 
	
	//允许请求方法, allowMethods = "GET,POST,DELETE,PUT"
	private String allowMethods = "*";
	
	//允许的请求头 allowHeaders = "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN"
	private String allowHeaders = "*";
	

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Origin", allowOrigin);
        res.addHeader("Access-Control-Allow-Credentials", allowCredentials);
        res.addHeader("Access-Control-Allow-Methods", allowMethods);
        res.addHeader("Access-Control-Allow-Headers", allowHeaders);
        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
            response.getWriter().println("ok");
            return;
        }
        chain.doFilter(request, response);
    }


	public String getAllowOrigin() {
		return allowOrigin;
	}


	public void setAllowOrigin(String allowOrigin) {
		this.allowOrigin = allowOrigin;
	}


	public String getAllowCredentials() {
		return allowCredentials;
	}


	public void setAllowCredentials(String allowCredentials) {
		this.allowCredentials = allowCredentials;
	}


	public String getAllowMethods() {
		return allowMethods;
	}


	public void setAllowMethods(String allowMethods) {
		this.allowMethods = allowMethods;
	}


	public String getAllowHeaders() {
		return allowHeaders;
	}


	public void setAllowHeaders(String allowHeaders) {
		this.allowHeaders = allowHeaders;
	}
    
}
