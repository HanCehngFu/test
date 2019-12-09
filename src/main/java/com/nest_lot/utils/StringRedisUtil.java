package com.nest_lot.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

/**
 * ClassName: StringRedisUtil String 数据类型 date: 2017年11月14日 下午8:15:07
 * 
 * @author wf
 * @version
 */
@Component
public class StringRedisUtil {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * set:(保存数据).
	 * 
	 * @author Joe Date:2017年11月14日下午8:15:01
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		ValueOperations<String, Object> operation = redisTemplate.opsForValue();
		operation.set(key, value);
	}

	public void set(String key, String value, long time) {
		ValueOperations<String, Object> operation = redisTemplate.opsForValue();
		operation.set(key, value, time, TimeUnit.MINUTES);
	}

	public void setLong(String key, int value) {
		ValueOperations<String, Object> operation = redisTemplate.opsForValue();
		operation.set(key, value);
	}

	public void setLongTime(String key, long value, long time) {
		ValueOperations<String, Object> operation = redisTemplate.opsForValue();
		operation.set(key, value, time, TimeUnit.MINUTES);
	}

	/**
	 * get:(得到数据).
	 * 
	 * @author Joe Date:2017年11月14日下午8:15:38
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		ValueOperations<String, Object> operation = redisTemplate.opsForValue();
		return operation.get(key);
	}

	/**
	 * 实例化 HashOperations 对象,可以使用 Hash 类型操作
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHash();
	}

	/**
	 * 实例化 ValueOperations 对象,可以使用 String 操作
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForValue();
	}

	/**
	 * 实例化 ListOperations 对象,可以使用 List 操作
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForList();
	}

	/**
	 * 实例化 SetOperations 对象,可以使用 Set 操作
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForSet();
	}

	/**
	 * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForZSet();
	}

}