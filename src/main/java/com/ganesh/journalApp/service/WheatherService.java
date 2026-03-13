package com.ganesh.journalApp.service;

import com.ganesh.journalApp.apiResponse.WheatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WheatherService {

@Value("${api.key}")
private String apiKey;
private String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

@Autowired
private RestTemplate restTemplate;
public WheatherApiResponse getWheather(String city){

    String replace = API.replace("CITY", city).replace("API_KEY", apiKey);

    ResponseEntity<WheatherApiResponse> exchange = restTemplate.exchange(replace, HttpMethod.GET, null, WheatherApiResponse.class);
    WheatherApiResponse body = exchange.getBody();
    return body;

}

}
