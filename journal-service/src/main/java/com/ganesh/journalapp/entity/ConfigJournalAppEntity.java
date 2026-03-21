package com.ganesh.journalapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config-journal")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfigJournalAppEntity {
    private String key;
    private String value;

}
