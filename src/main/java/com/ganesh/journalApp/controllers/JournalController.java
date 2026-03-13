package com.ganesh.journalApp.controllers;

import com.ganesh.journalApp.entity.JournalEntry;
import com.ganesh.journalApp.entity.User;
import com.ganesh.journalApp.service.JournalEntryService;
import com.ganesh.journalApp.service.UserService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journalEntry")
@AllArgsConstructor
public class JournalController {

private  final JournalEntryService journalEntryService;
private final UserService userService;
    @GetMapping("/username")
    public ResponseEntity<List<JournalEntry>> getAllEntriesForUser() {
        try {
            return ResponseEntity.ok(journalEntryService.getAll());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        try {
            JournalEntry saved = journalEntryService.saveEntry(entry);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId id) {
        try {
            JournalEntry entry = journalEntryService.getEntryById(id);
            return ResponseEntity.ok(entry);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable ObjectId id) {
        journalEntryService.deleteEntry(id);
        return ResponseEntity.noContent().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateEntry(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry entry) {

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

        JournalEntry updated = journalEntryService.saveEntry(old);

        return ResponseEntity.ok(updated);
    }
    }



