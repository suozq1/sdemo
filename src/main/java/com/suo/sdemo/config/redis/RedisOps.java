package com.suo.sdemo.config.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
/**
 * redis操作接口类
 * @author 锁战强
 *
 */
@Component
public final class RedisOps{
	
	@Value("${spring.redis.key-prefix}")
	private String KEY_PREFIX;
	
	@Value("${spring.redis.lock-expire-time:30}")
	private Long LOCK_EXPIRE_TIME;
	
	private static final String LOCK_KEY_PREFIX = "REDIS-LOCK";

	@Autowired
	RedisTemplate<String,Object> redisTemplate;
	
	public <T> void set(String key,T value) {
		redisTemplate.opsForValue().set(addPrefix(key), value);
	}
	
	
	public <T> void set(String key,T value,long timeout, TimeUnit unit) {
		redisTemplate.opsForValue().set(addPrefix(key), value,timeout,unit);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) redisTemplate.opsForValue().get(addPrefix(key));
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T getAndSet(String key,T value) {
		return (T) redisTemplate.opsForValue().getAndSet(addPrefix(key), value);
	}
	
	public boolean lock(String key) {
	    key = addPrefix(LOCK_KEY_PREFIX,key);
	    return redisTemplate.opsForValue().setIfAbsent(key, 1, LOCK_EXPIRE_TIME,TimeUnit.MINUTES);
	}
	
	public boolean unlock(String key) {
	    key = addPrefix(LOCK_KEY_PREFIX,key);
	    return redisTemplate.delete(key);
	}
	
	/**
	 *   追加前缀
	 * @param key
	 * @return
	 */
	private String addPrefix(String key) {
		return addPrefix(KEY_PREFIX,key);
	}
	
	private String addPrefix(String prefix,String key) {
	    StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(":").append(key);
        return sb.toString();
	}
	
}
