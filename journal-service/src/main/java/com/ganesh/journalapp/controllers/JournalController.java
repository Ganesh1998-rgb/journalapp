package com.ganesh.journalapp.controllers;


import com.ganesh.common.dto.JournalEntryRequestDTO;
import com.ganesh.common.dto.JournalEntryResponseDTO;
import com.ganesh.journalapp.entity.JournalEntry;
import com.ganesh.journalapp.service.JournalEntryService;
import com.ganesh.journalapp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.AllArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journalEntry")
@AllArgsConstructor
@Tag(name = "Journal Entry APIs", description = "APIs for managing journal entries")
public class JournalController {

    private final JournalEntryService journalEntryService;
    private final UserService userService;

    @Operation(summary = "Get all journal entries", description = "Fetch all journal entries for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Entries fetched successfully")
    @GetMapping("/username")
    public ResponseEntity<List<JournalEntry>> getAllEntriesForUser() {
        try {
            return ResponseEntity.ok(journalEntryService.getAll());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new journal entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Journal entry created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<JournalEntryResponseDTO> createEntry(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Journal entry object to be created")
            @RequestBody JournalEntryRequestDTO entry) {
        try {
            JournalEntryResponseDTO journalEntryResponseDTO = journalEntryService.saveEntry(entry);
            return ResponseEntity.status(HttpStatus.CREATED).body(journalEntryResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get journal entry by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entry found"),
            @ApiResponse(responseCode = "404", description = "Entry not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getEntryById(
            @Parameter(description = "Journal entry ID")
            @PathVariable ObjectId id) {
        try {
            JournalEntry entry = journalEntryService.getEntryById(id);
            return ResponseEntity.ok(entry);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete journal entry by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entry deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Entry not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(
            @Parameter(description = "Journal entry ID")
            @PathVariable ObjectId id) {
        journalEntryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }


   /* @Operation(summary = "Update journal entry by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entry updated successfully"),
            @ApiResponse(responseCode = "404", description = "Entry not found or not owned by user")
    })

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntryResponseDTO> updateEntry(
            @Parameter(description = "Journal entry ID")
            @PathVariable ObjectId id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated journal entry data")
            @RequestBody JournalEntryRequestDTO entry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUserName(username);

        boolean exists = user.getJournalEntries()
                .stream()
                .anyMatch(x -> x.getId().equals(id));

        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        JournalEntry old = journalEntryService.getEntryById(id);

        old.setTitle(entry.getTitle());
        old.setContent(entry.getContent());
        old.setUpdatedAt(LocalDateTime.now());

        JournalEntryResponseDTO journalEntryResponseDTO = journalEntryService.saveEntry(old);

        return ResponseEntity.ok(journalEntryResponseDTO);
    }*/
}