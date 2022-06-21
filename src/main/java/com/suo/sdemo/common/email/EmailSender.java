package com.suo.sdemo.common.email;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.suo.sdemo.buss.sys.entity.SysUser;
import com.suo.sdemo.common.ErrorCode;
import com.suo.sdemo.common.exception.AppException;
@Component
public class EmailSender {
	
	  private static final String EMAIL_TEMP_PATH="/email";
	  
	  private static final Map<String,String> TEMP_CACHE = new HashMap<>();
	  
	  private static final String TEMP_EXT = ".ftl";
	  
	  private static final String WELCOME_I18N_CODE = "welcome";
	  
	  @Autowired
	  private MessageSource messageSource;
	  
	  public static enum EmailTemp{
		  SIGN_UP("/signup")
		  ;
		  private String path;
		  
		  EmailTemp(String path){
			  this.setPath(path);
		  }

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
	  }
	  
	  @Value("classpath:email/signup.ftl")
	  private Resource signup;
	
	  @Autowired
	  private JavaMailSender mailSender;
	  
	  @Autowired
	  ResourceLoader resourceLoader;
	  
	  @Value("${spring.mail.from}")
	  private String from;
	  
	  @Async
	  public void sendSignUpEmail(HttpServletRequest request,SysUser user) throws NoSuchMessageException, Exception {
		  Locale locale = RequestContextUtils.getLocale(request);
		  String contentTemp = getTemplate(locale, EmailTemp.SIGN_UP);
		  Map<String,String> param = new HashMap<>();
		  param.put("nickname", user.getNickname());
		  sendMimeMail(user.getEmail(), messageSource.getMessage(WELCOME_I18N_CODE, null, locale), replace(contentTemp, param));
	  }
	  
	  private String replace(String temp,Map<String,String> param) {
		  String reg = "\\$\\{(\\w+)\\}";
		  Pattern pattern  = Pattern.compile(reg);
		  Matcher matcher = pattern.matcher(temp);
		  while(matcher.find()) {
			  String key = matcher.group(1);
			  if(param.containsKey(key)) {
				  temp = temp.replaceAll("\\$\\{"+key+"\\}", param.get(key));
			  }
		  }
		  return temp;
	  }
	  
	  public String getTemplate(Locale locale,EmailTemp emailTemp) {
		  String key = emailTemp.getPath()+"_"+locale.toString();
		  if(!TEMP_CACHE.containsKey(key)) {
			  StringBuilder sb = new StringBuilder();
			  Resource r = findResource(locale, emailTemp);
			  try(InputStreamReader input =new InputStreamReader(r.getInputStream(),Charset.forName("UTF-8"))){
				  char[] chars = new char[100];
				  while(input.read(chars)!=-1) {
					  sb.append(chars);
				  }
			  }catch (Exception e) {
				  throw new AppException(e,ErrorCode.FILE_READ_FAIL);
			  }
			  TEMP_CACHE.put(key, sb.toString());
		  }
		  return TEMP_CACHE.get(key);
	  }
	  
	  private Resource findResource(Locale locale,EmailTemp emailTemp) {
		  StringBuilder sb = new StringBuilder("classpath:");
		  sb.append(EMAIL_TEMP_PATH).append(emailTemp.getPath());
		  Resource r = null;
		  if(locale!=null) {
			  String[] localeArr = {locale.toString(),locale.getLanguage()};
			  for(String localeStr : localeArr) {
				   String filePath = sb.toString() + "_"+localeStr+ TEMP_EXT;
				   r = resourceLoader.getResource(filePath);
				   if(r.exists()) {
					   break;
				   }
			  }
		  }
		  if(r==null) {
			  sb.append(TEMP_EXT);
			  r = resourceLoader.getResource(sb.toString());
		  }
		  return r;
	  }
	  
	  
	  /**
	   * 发送简单文本邮件
	   * @param to 接收人邮箱
	   * @param subject 主题
	   * @param content 内容
	   */
	  public void sendSimpleMail(String to, String subject, String content) {
	      //创建一个简单文本邮件的对象
		  SimpleMailMessage message = new SimpleMailMessage();
	      //赋予相应的内容
	      message.setTo(to);
	      message.setSubject(subject);
	      message.setText(content);
	      message.setFrom(from);
	      //将邮件对象赋予邮件发送器
	      mailSender.send(message);
	  }

	  public void sendMimeMail(String to, String subject, String content) throws Exception {
	        //创建MIME样式的电子邮件对象
	        MimeMessage message = mailSender.createMimeMessage();

	        //用于填充MimeMessage的帮助类
	        //参数1填入MimeMessage对象
	        //参数2表示是否创建支持替代文本、内联元素和附件的多部分消息
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        //参数2表示是否使用默认内容类型为HTML邮件应用内容类型"text/html"
	        helper.setText(content, true);
	        mailSender.send(message);
	    }


}
