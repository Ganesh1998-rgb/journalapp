package com.ganesh.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    @Disabled
    void testEmail() {

        redisTemplate.opsForValue().set("email","gborase@gmail.com");

        Object salary = redisTemplate.opsForValue().get("salary");

        System.out.println(salary);
    }
}

