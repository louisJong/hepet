package com.project.hepet.test;

import org.junit.Test;

import com.project.hepet.redis.RedisClient;

public class RedisTest {
	
	@Test
	public void test_01(){
		RedisClient.set("keytest2", RedisClient.incrby("keytest1", 2l), 2000000);
		System.out.println(RedisClient.get("keytest2"));
	}
	
}
