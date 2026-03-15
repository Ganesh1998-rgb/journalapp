package com.ganesh.journalApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganesh.journalApp.apiResponse.WheatherApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;

    public void setValue(String key, Object value, Long ttl) {

        try {
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);

        } catch (Exception e) {
            log.error("Error while saving data to Redis", e);
        }
    }

    public <T> T getValue(String key, Class<T> type) {

        String json = redisTemplate.opsForValue().get(key);

        if (json == null) {
            log.info("Key {} not found in Redis", key);
            return null;
        }

        try {
            return objectMapper.readValue(json, type);

        } catch (JsonProcessingException e) {
            log.error("Error while fetching data from Redis", e);
            return null;
        }
    }
}




