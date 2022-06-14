package com.suo.sdemo.buss.sys.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.suo.sdemo.buss.sys.entity.SysUser;
import com.suo.sdemo.buss.sys.pojo.form.LoginForm;
import com.suo.sdemo.common.AppResponse;
import com.suo.sdemo.common.ErrorCode;
import com.suo.sdemo.common.exception.AppException;
import com.suo.sdemo.util.AppUtils;
import com.suo.sdemo.util.CaptchaUtils;
import com.suo.sdemo.util.CaptchaUtils.SessionDao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value="登录",tags="登录模块")
@RestController
@Validated
public class LoginController {
	
	@Autowired
	SessionDao sessionDao;
	
	@ApiOperation("登录")
	@PostMapping("/login")
	public AppResponse<Object> login(@RequestBody @Validated LoginForm user,HttpServletRequest request){
		AppResponse<Object> r = AppResponse.success(request);
		if(!CaptchaUtils.verAndClear(user.getCkey(), user.getCaptcha(), sessionDao)) {
			r.setErrorCode(ErrorCode.CAPTCHA_ERROR);
			return r;
		}
		AuthenticationToken token = new UsernamePasswordToken(user.getEmail(),user.getPassword().toUpperCase());
		SecurityUtils.getSubject().login(token);
		SysUser u = AppUtils.getCurrentUser();
		u.setSessionId(SecurityUtils.getSubject().getSession().getId());
		r.setData(u);
		return r;
	}
		
	@ApiOperation("验证码")
	@GetMapping("/captcha.png")
	public void captcha(String ckey,HttpServletRequest request,HttpServletResponse response) throws IOException {
		if(StringUtils.isEmpty(ckey)) {
			throw new AppException(ErrorCode.PARAM_MISS);
		}
		CaptchaUtils.out(ckey,sessionDao,response);
	}
	
}
