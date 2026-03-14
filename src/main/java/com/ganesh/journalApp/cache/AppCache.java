package com.ganesh.journalApp.cache;

import com.ganesh.journalApp.entity.ConfigJournalAppEntity;
import com.ganesh.journalApp.repository.ConfigruationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppCache {

    public enum Keys{
         WEATHER_API;
    }

    private final ConfigruationRepository configruationRepository;
   public  Map<String,String> APP_CACHE=new HashMap<>();

    @PostConstruct
    public void init(){
        List<ConfigJournalAppEntity> all = configruationRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all){
            APP_CACHE.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
        System.out.println("APP_CACHE : " + APP_CACHE);
    }
}
