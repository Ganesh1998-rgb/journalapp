package com.ganesh.journalapp.cache;

import com.ganesh.journalapp.entity.ConfigJournalAppEntity;
import com.ganesh.journalapp.repository.ConfigruationRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppCache {

    public enum Keys {
        WEATHER_API;
    }

    private final ConfigruationRepository configruationRepository;
    public Map<String, String> APP_CACHE = new HashMap<>();

    public  String getValue(Keys key) {
        return APP_CACHE.get(key.name());
    }

    @PostConstruct
    public void init() {
        List<ConfigJournalAppEntity> all = configruationRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all) {
            APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }

        log.info("App_Cache" + APP_CACHE);
    }
}
