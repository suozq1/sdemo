package com.suo.sdemo.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suo.sdemo.common.AppResponse;
import com.suo.sdemo.common.ErrorCode;
/**
 * 处理Web相关的通用Util类
 * @author 锁战强
 *
 */
public class WebUtils {
	
	private WebUtils() {}
	
	public static HttpServletRequest toHttp(ServletRequest request) {
		return (HttpServletRequest) request;
	}
	
	public static HttpServletResponse toHttp(ServletResponse response) {
		return (HttpServletResponse) response;
	}
	/**
	 * 不会关闭流
	 * 把对象转json串，写回
	 * @param response
	 * @param obj
	 */
	public static void writeObject(ServletResponse response,Object obj) {
		HttpServletResponse r = toHttp(response);
		r.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter pw = r.getWriter();
			BufferedWriter bw = new BufferedWriter(pw);
			bw.write(JacksonUtils.getJson(obj));
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 不关闭流
	 * @param request
	 * @param response
	 * @param ecode
	 */
	public static void writeErrorCode(ServletRequest request, ServletResponse response,ErrorCode ecode) {
		AppResponse<?>  r = AppResponse.fail(ecode, toHttp(request));
		writeObject(response, r);
	}
	
	/**
	 * 下载文件
	 * @param response
	 * @param in
	 * @param fileName
	 * @throws IOException
	 */
	public static void downloadFile(ServletResponse response,InputStream in , String fileName) throws IOException {
    	((HttpServletResponse) response).setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+","%20"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
    	FileUtils.write(in, response.getOutputStream());
	}
}
