package com.ganesh.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalCreatedEventDTO {
    private String userEmail;
    private String entryTitle;
    private String entryContent;
}

