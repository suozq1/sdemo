package com.suo.sdemo.common;

/**
 * 错误码与国际化文件中对应
 *
 * @author suozq
 */
public enum ErrorCode {
    UNKNOWN(-1, "Unknown"),
    SUCCESS(0, "Success"),
    CUSTOM_MSG(-2,"自定义消息"),
    //1000 - 通用基础:任何项目都会用到，包含基础框架spring等相关错误
    COMMON_FAIL(1000, "Common failure"),
    PARAM_NULL(ErrorCode.CODE_PARAM_NULL, "Input parameter is null"),
    PARAM_MISS(1002, "Miss required parameter"),
    PARAM_FORMAT(1003, "Input parameter format is wrong"),
    METHOD_NOTFOUND(1004, "Required method not found"),
    SERVICE_UNINIT(1005, "Service hasn't initialized"),
    INCORRECT_USERNAME_OR_PASSWORD(1006,"Incorrect username or password"),
    UNAUTHORIZED(1007,"Unauthorized"),
    TOKEN_NOT_FOUND(1008,"Token not found"),
    CAPTCHA_ERROR(1009,"Captcha error"),
    FILE_WRITE_FAIL(1010, "文件写入失败"),
    FILE_MODULE_NO_MATCH(1011,"文件与模块不匹配"),
    EXCEL_HEADER_NOT_FOUND(1012,"Excel头未找到"),
    FILE_READ_FAIL(1013,"文件读取失败,文件可能不存在"),
    
    //2000 - 第三方相关：调用工具类、第三方接口、第三方服务、第三方jar包等报错，范围：不是所有项目都会用到
   
    
    //3000 - 数据库相关:和数据库相关的错误
    CURRENT_STATUS_CAN_NOT_DEL(3001,"当前状态不能删除"),
    NO_PERMISSION_TO_OPERATE(3002,"没有操作权限"),
    
    //4000 - 业务相关：和业务相关报错
    USER_DEL_OR_QUIT(4011,"用户离职或不存在"),
    ;
	public static final String CODE_PARAM_NULL = "1001";

    private int errorCode;
    private String msg;

    ErrorCode(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
    ErrorCode(String errorCode, String msg) {
        this.errorCode = Integer.parseInt(errorCode);
        this.msg = msg;
    }

    public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	public static ErrorCode valueOf(int errorCode) {
        for (ErrorCode type : ErrorCode.values()) {
            if (type.getErrorCode() == errorCode) {
                return type;
            }
        }
        return null;
    }
	
}
