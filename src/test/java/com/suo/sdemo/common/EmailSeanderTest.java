package com.suo.sdemo.common;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.suo.sdemo.common.email.EmailSender;
import com.suo.sdemo.common.email.EmailSender.EmailTemp;

@SpringBootTest
public class EmailSeanderTest {
	
	@Autowired
	EmailSender emailSender;
	
	@Test
	public void sendMimeMailTest() throws Exception {
		emailSender.sendMimeMail("915856893@qq.com", "欢迎", "测试邮件");
	}
	
	@Test
	public void getTemplateTest() {
		Locale locale = Locale.CANADA;
		String  temp = emailSender.getTemplate(locale, EmailTemp.SIGN_UP);
		System.out.println(temp);
	}
	
	public void sendSignUpEmailTest() {
	}
}
