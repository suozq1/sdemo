package com.suo.sdemo.util;

import java.awt.Font;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;


public class CaptchaUtils {
	public static interface SessionDao{
		public void set(String key,String value) ;
		public String delKey(String key);
	}
	
	@Component
	public static class RedisSessionDao implements SessionDao{
		
		@Autowired
		StringRedisTemplate stringRedisTemplate;
		
		@Override
		public void set(String key, String value) {
			stringRedisTemplate.boundValueOps(key).set(value,2,TimeUnit.HOURS);
		}

		@Override
		public String delKey(String key) {
			String val = stringRedisTemplate.opsForValue().get(key);
			stringRedisTemplate.delete(key);
			return val;
		}
		
	}
	
    private static final String SESSION_KEY = "captcha";
    private static final int DEFAULT_LEN = 4;  // 默认长度
    private static final int DEFAULT_WIDTH = 130;  // 默认宽度
    private static final int DEFAULT_HEIGHT = 48;  // 默认高度
    
    public static void out(String key,SessionDao sessionDao,HttpServletResponse response) throws IOException {
    	SpecCaptcha specCaptcha = new SpecCaptcha(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_LEN);
        setHeader(response);
        sessionDao.set(key, specCaptcha.text().toLowerCase());
        specCaptcha.out(response.getOutputStream());
    }

    /**
     * 输出验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static void out(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        out(DEFAULT_LEN, request, response);
    }
    
    private CaptchaUtils() {}

    /**
     * 输出验证码
     *
     * @param len      长度
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static void out(int len, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        out(DEFAULT_WIDTH, DEFAULT_HEIGHT, len, request, response);
    }

    /**
     * 输出验证码
     *
     * @param width    宽度
     * @param height   高度
     * @param len      长度
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static void out(int width, int height, int len, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        out(width, height, len, null, request, response);
    }

    /**
     * 输出验证码
     *
     * @param font     字体
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static void out(Font font, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        out(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_LEN, font, request, response);
    }

    /**
     * 输出验证码
     *
     * @param len      长度
     * @param font     字体
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static void out(int len, Font font, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        out(DEFAULT_WIDTH, DEFAULT_HEIGHT, len, font, request, response);
    }

    /**
     * 输出验证码
     *
     * @param width    宽度
     * @param height   高度
     * @param len      长度
     * @param font     字体
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static void out(int width, int height, int len, Font font, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        SpecCaptcha specCaptcha = new SpecCaptcha(width, height, len);
        if (font != null) {
            specCaptcha.setFont(font);
        }
        out(specCaptcha, request, response);
    }


    /**
     * 输出验证码
     *
     * @param captcha  Captcha
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static void out(Captcha captcha, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        setHeader(response);
        request.getSession().setAttribute(SESSION_KEY, captcha.text().toLowerCase());
        captcha.out(response.getOutputStream());
    }

    /**
     * 验证验证码
     *
     * @param code    用户输入的验证码
     * @param request HttpServletRequest
     * @return 是否正确
     */
    public static boolean ver(String code, HttpServletRequest request) {
        if (code != null) {
            String captcha = (String) request.getSession().getAttribute(SESSION_KEY);
            return code.trim().toLowerCase().equals(captcha);
        }
        return false;
    }
    /**
     * 验证并清除
     * @param key redis中key
     * @param code 用户输入的验证码
     * @param sessionDao redis读取
     * @return 是否正确
     */
    public static boolean verAndClear(String key,String code, SessionDao sessionDao) {
        if (key!=null&&code != null) {
            String captcha = sessionDao.delKey(key);
            return code.trim().toLowerCase().equals(captcha);
        }
        return false;
    }

    /**
     * 清除session中的验证码
     *
     * @param request HttpServletRequest
     */
    public static void clear(HttpServletRequest request) {
        request.getSession().removeAttribute(SESSION_KEY);
    }

    /**
     * 设置相应头
     *
     * @param response HttpServletResponse
     */
    public static void setHeader(HttpServletResponse response) {
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }

}
