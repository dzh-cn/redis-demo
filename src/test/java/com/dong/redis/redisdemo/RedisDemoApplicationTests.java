package com.dong.redis.redisdemo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDemoApplicationTests {

	@Resource
	RedisTemplate<String, String> redisTemplate;

	Logger logger = LoggerFactory.getLogger(RedisDemoApplicationTests.class);

	@Before
	public void before() {
//		redisTemplate.opsForValue().set("name", "jack");

//		redisTemplate.opsForSet().add("name-set", "set1", "set2", "set3", "set1");

//		redisTemplate.opsForHash().put("map", "name", "dongzhihua");
//		redisTemplate.opsForHash().put("map", "age", "18");
//		redisTemplate.opsForHash().put("map", "gender", "F");

		redisTemplate.opsForList().leftPushAll("list", "english", "mathematics", "history", "physics");
	}

	@Test
	public void contextLoads() {
		String str = redisTemplate.opsForValue().get("name");
		logger.info("\n\t**RedisDemoApplicationTests.contextLoads name : {}", str);
		logger.info("\n\t**RedisDemoApplicationTests.contextLoads set : {}", redisTemplate.opsForSet().members("name-set"));

		Collection keys = redisTemplate.opsForHash().keys("map");
		logger.info("\n\t**RedisDemoApplicationTests.contextLoads map keys : {}", keys);

		Map map = new HashMap();
		for (Object s : keys) {
			Object value = redisTemplate.opsForHash().get("map", s);
			map.put(s, value);
		}
		logger.info("\n\t**RedisDemoApplicationTests.contextLoads map : {}", map);

		long size = redisTemplate.opsForList().size("list");
		logger.info("\n\t**RedisDemoApplicationTests.contextLoads list size : {}", size);
		logger.info("\n\t**RedisDemoApplicationTests.contextLoads list : {}", redisTemplate.opsForList().range("list", 0, size - 1));
		logger.info("\n\t**RedisDemoApplicationTests.contextLoads list : {}", redisTemplate.opsForList().range("list", 0, 100));
	}

	@After
	public void after() {
		redisTemplate.opsForList().getOperations().delete("list");
	}
}
