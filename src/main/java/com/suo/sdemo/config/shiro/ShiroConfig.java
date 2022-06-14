package com.suo.sdemo.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.suo.sdemo.config.shiro.filter.JsonFormAuthenticationFilter;
import com.suo.sdemo.config.shiro.filter.JsonPermissionsAuthorizationFilter;

@Configuration
public class ShiroConfig {
	
	@Value("${shiro.session.timeout}")
	private Long globalSessionTimeout;
	
	@Value("${spring.redis.host}")
	private String redisHost;
	
	@Value("${spring.redis.port}")
	private Integer redisPort;
	
	@Value("${spring.redis.password:}")
	private String password;
	
	@Value("${server.servlet.context-path}")
	private String contextPath;
	
	public static final String TOKEN_KEY = "JSESSIONID";
	
	//@Bean
//	public TokenFilter tokenFilter(){
//		TokenFilter f=new TokenFilter();
//		return f;
//	};
	
	
	@Bean
	public ShiroRealm shiroRealm(){
		return new ShiroRealm();
	};
	
	@Bean
	public DefaultWebSecurityManager securityManager(){
		DefaultWebSecurityManager d=new DefaultWebSecurityManager();
		d.setSessionManager(sessionManager());
		d.setRealm(shiroRealm());
		d.setCacheManager(cacheManager());
		return d;
	}
	
	public CacheManager cacheManager(){
	    RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
	}
	
	@Bean
	public DefaultWebSessionManager sessionManager(){
		DefaultWebSessionManager dwsm=new DefaultWebSessionManager();
		dwsm.setGlobalSessionTimeout(globalSessionTimeout);
		dwsm.setSessionDAO(redisSessionDAO());
		dwsm.getSessionIdCookie().setName(TOKEN_KEY);
		return dwsm;
	}
	
	@Bean
	public RedisSessionDAO redisSessionDAO(){
		RedisSessionDAO rs=new RedisSessionDAO();
		rs.setRedisManager(redisManager());
		return rs;
	}
	
	
	@Bean
	public RedisManager redisManager(){
		RedisManager rm=new RedisManager();
		rm.setHost(redisHost+":"+redisPort);
		if(!StringUtils.isEmpty(password)) {
			rm.setPassword(password);
		}
		return rm;
	}
	
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(){
		ShiroFilterFactoryBean sff=new ShiroFilterFactoryBean();
		sff.setSecurityManager(securityManager());
//		sff.setLoginUrl("/#/login");
//		sff.setSuccessUrl("/home");
//		sff.setUnauthorizedUrl("/login");
		Map<String,Filter> filters = sff.getFilters();
		filters.put("perms", new JsonPermissionsAuthorizationFilter());
		filters.put("authc", new JsonFormAuthenticationFilter());
		
//		sff.setFilters(filters);
		Map<String,String> filterChain = new LinkedHashMap<>();
		
		filterChain.put("/favicon.ico", "anon");
		filterChain.put("/login", "anon");
		filterChain.put("/captcha.png", "anon");
		filterChain.put("/static/**", "anon");
		//swagger放行
		filterChain.put("/swagger-ui/**", "anon");
		filterChain.put("/v2/**", "anon");
		filterChain.put("/swagger-resources/**", "anon");

		filterChain.put("/**", "authc");
		sff.setFilterChainDefinitionMap(filterChain);
		return sff;
	}
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	@Bean
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}
	
	
	
	
}
