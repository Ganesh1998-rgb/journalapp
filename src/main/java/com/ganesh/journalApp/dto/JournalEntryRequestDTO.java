package com.ganesh.journalApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JournalEntryRequestDTO {

    @Schema(description = "Title of the journal entry", example = "My First Entry")
    @NotBlank(message = "Title is required")
    private String title;

    @Schema(description = "Content of the journal entry", example = "Today I learned Spring Boot...")
    private String content;

}