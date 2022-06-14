package com.suo.sdemo.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.suo.sdemo.common.AppResponse;
import com.suo.sdemo.common.ErrorCode;
import com.suo.sdemo.common.exception.AppException;

/**
 * 通用异常处理类
 * @author 锁战强
 *
 */
@ControllerAdvice
public class AppExceptionHandler{
	
	  static final Logger log = LoggerFactory.getLogger(AppExceptionHandler.class);
	  
	    /**
	     * json数据异常处理
	     * @param request
	     * @param e
	     * @return
	     */
	    @ExceptionHandler({AppException.class,Exception.class})  
	    @ResponseBody  
	    public AppResponse<?> jsonExceptionHandler(HttpServletRequest request, Exception e) {  
	    	log.debug(e.getMessage(),e);//打印异常日志
	    	AppResponse<?> r=new AppResponse<>(ErrorCode.UNKNOWN,request);
	    	if(e instanceof AppException){ 
	    		//通用处理
	    		AppException oe=(AppException) e;
	    		r.setErrorCode(oe.getErrorCode());
	    		if(oe.getErrorCode() == ErrorCode.CUSTOM_MSG.getErrorCode()) {
	    			r.setMsg(oe.getMessage());
	    		}
	    	}else if(e instanceof MethodArgumentNotValidException) {
	    		//方法传递参数验证
	    		MethodArgumentNotValidException oe = (MethodArgumentNotValidException) e;
	    		List<ObjectError> err = oe.getBindingResult().getAllErrors();
	    		if(err!=null&&err.size()>0) {
	    			r.setErrorCode(err.get(0).getDefaultMessage());
	    		}
	    	}else if(e instanceof ConstraintViolationException) {
                //方法传递参数验证
	    	    ConstraintViolationException oe = (ConstraintViolationException) e;
	    	    String[]  arr = oe.getMessage().split(":");
	    	    if(arr!=null && arr.length >1) {
	    	        r.setErrorCode(arr[1]);
	    	    }
            }else if(e instanceof AuthenticationException && e.getCause() instanceof AppException) {
	    		//登录授权
	    		AppException oe = (AppException) e.getCause();
	    		r.setErrorCode(oe.getErrorCode());
	    	}else if (e instanceof BindException) {
	    		BindException be = (BindException) e;
	    		if(be.hasErrors()) {
	    			String eCode = be.getAllErrors().get(0).getDefaultMessage();
	    			r.setErrorCode(eCode);
	    		}
	    	}
	        return r;  
	    }  
	    
	    @ResponseBody
	    @ResponseStatus(HttpStatus.UNAUTHORIZED)//response状态码
	    @ExceptionHandler(AuthorizationException.class)//捕捉的Shiro异常
	    public AppResponse<?> authorizationExceptionHandler(HttpServletRequest request, AuthorizationException e) {
	    	return AppResponse.fail(ErrorCode.UNAUTHORIZED, request);
	    }
	
	    
	    
		
}
