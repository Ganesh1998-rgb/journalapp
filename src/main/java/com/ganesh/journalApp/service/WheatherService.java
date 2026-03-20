package com.ganesh.journalApp.service;

import com.ganesh.journalApp.apiResponse.WheatherApiResponse;
import com.ganesh.journalApp.cache.AppCache;
import com.ganesh.journalApp.constant.PlaceHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class WheatherService {

    private   final RestTemplate restTemplate;
    private final  WebClient webClient;
private final RedisService redisService;

@Value("${api.key}")
private String apiKey;

private final  AppCache appCache;


public WheatherApiResponse getWheather(String city) {
    WheatherApiResponse value = redisService.getValue("weather_of_" + city, WheatherApiResponse.class);
    if (value != null) {
        return value;
    } else {
        String replace = appCache.APP_CACHE.get(AppCache.Keys.WEATHER_API.name().toLowerCase()).replace(PlaceHolder.CITY, city).replace(PlaceHolder.API_KEY, apiKey);

        ResponseEntity<WheatherApiResponse> exchange = restTemplate.exchange(replace, HttpMethod.GET, null, WheatherApiResponse.class);
        WheatherApiResponse body = exchange.getBody();
        if(body!=null){
            redisService.setValue("weather_of_" + city,body,300L);
        }
        return body;
    }

}
    public WheatherApiResponse getWeather(String city) {
        WheatherApiResponse value = redisService.getValue("weather_of_"+city, WheatherApiResponse.class);
        if (value != null) {
            return value;
        }
        else{
            String weatherApi = appCache.APP_CACHE.get(AppCache.Keys.WEATHER_API.name().toLowerCase()).replace(PlaceHolder.CITY, city).replace(PlaceHolder.API_KEY, apiKey);
            WheatherApiResponse block = webClient
                    .get()
                    .uri(weatherApi)
                    .retrieve()
                    .bodyToMono(WheatherApiResponse.class)
                    .block();
            if(block!=null){
                redisService.setValue("weather_of_"+city,block,300L);
            }

            return block;
        }
       
    }

}
