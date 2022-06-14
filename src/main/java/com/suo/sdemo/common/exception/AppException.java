/**
 * @author 锁战强
 */

package com.suo.sdemo.common.exception;

import com.suo.sdemo.common.ErrorCode;

/**
 * 通用异常类
 *
 * @author suo
 */
public class AppException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 1L;
    /**
     * 错误码对应ErrorCode
     */
    private int errorCode = ErrorCode.UNKNOWN.getErrorCode();
    /**
     * 错误信息
     */
    private String msg;

    public AppException(ErrorCode eCode) {
        super(eCode.getMsg());
        this.errorCode = eCode.getErrorCode();
        this.msg = eCode.getMsg();
    }
    public AppException(int errorCode) {
        super(errorCode+"");
        this.errorCode = errorCode;
    }

    public AppException(String msg) {
        super(msg);
        this.errorCode = ErrorCode.CUSTOM_MSG.getErrorCode();
        this.msg = msg;
    }

    public AppException(Throwable exp) {
        super(exp);
        this.errorCode = ErrorCode.UNKNOWN.getErrorCode();
        this.msg = exp.getMessage();
    }

    public AppException(ErrorCode eCode, String message) {
        super(message);
        this.errorCode = eCode.getErrorCode();
        this.msg = message;
    }

    public AppException(Throwable exp,ErrorCode eCode) {
        super(exp);
        this.errorCode = eCode.getErrorCode();
        this.msg = eCode.getMsg();
    }
	
    
    
	public int getErrorCode() {
		return errorCode;
	}
	public String getMsg() {
		return msg;
	}
    
   
}
