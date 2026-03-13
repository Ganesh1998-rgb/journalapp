package com.ganesh.journalApp.service;

import com.ganesh.journalApp.apiResponse.WheatherApiResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

@Value("${api.key}")
private String apiKey;
private String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";



public WheatherApiResponse getWheather(String city){

    String replace = API.replace("CITY", city).replace("API_KEY", apiKey);

    ResponseEntity<WheatherApiResponse> exchange = restTemplate.exchange(replace, HttpMethod.GET, null, WheatherApiResponse.class);
    WheatherApiResponse body = exchange.getBody();
    return body;

}


    public WheatherApiResponse getWeather(String city) {

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("api.weatherstack.com")
                        .path("/current")
                        .queryParam("access_key", apiKey)
                        .queryParam("query", city)
                        .build())
                .retrieve()
                .bodyToMono(WheatherApiResponse.class)
                .block();   // blocking only for testing
    }

}
