package com.suo.sdemo.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author 锁战强
 *
 * @param <T>
 */
public class AppResponse<T> {
	
	private static Logger log = LoggerFactory.getLogger(AppResponse.class);
	
    /**
     * 错误码
     */
    private int errorCode = -1;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 返回信息，该值由错误码和其对应的国际化文件决定，不需要设置
     */
    private String msg;
    

	@JsonIgnore
    private HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    //由于是ajax请求，前端不能获取国际化信息，所有在Response中自动获取，需要传入request
    public AppResponse(int errorCode, HttpServletRequest request) {
        this.request = request;
        setErrorCode(errorCode);
    }

    public AppResponse(int errorCode) {
        this.errorCode = errorCode;
    }

    public AppResponse(ErrorCode code, HttpServletRequest request) {
		this(code.getErrorCode(),request);
	}

	public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        if(request!=null){
        	WebApplicationContext ac = RequestContextUtils.findWebApplicationContext(request);
        	try{
        		this.setMsg(ac.getMessage(errorCode + "", null, RequestContextUtils.getLocale(request)));
        	}catch(Exception e){
        		log.debug(e.getMessage(),e);
        	}
        }
    }
    
    public void setErrorCode(String errorCodeStr) {
    	setErrorCode(Integer.parseInt(errorCodeStr.trim()));
    }
    
    public void setErrorCode(ErrorCode e){
    	setErrorCode(e.getErrorCode());
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public static <S> AppResponse<S> success(HttpServletRequest request) {
		return new AppResponse<>(ErrorCode.SUCCESS,request);
	}
	
	public static <S> AppResponse<S> success(S data,HttpServletRequest request) {
		AppResponse<S> r = success(request);
		r.setData(data);
		return r;
	}
	
	public static <S> AppResponse<S> fail(HttpServletRequest request){
		return new AppResponse<>(ErrorCode.UNKNOWN,request);
	}

	public static <S> AppResponse<S> fail(ErrorCode errorCode,HttpServletRequest request) {
		return new AppResponse<>(errorCode, request);
	}
	
}
