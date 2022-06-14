package com.suo.sdemo.common.exception;

import com.suo.sdemo.common.ErrorCode;

public class AppObjException extends AppException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object error;
	
	public AppObjException(Object error) {
		super(ErrorCode.UNKNOWN);
		this.error = error;
	}
	
	public AppObjException(ErrorCode eCode) {
		super(eCode);
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}
	
}
