package com.ganesh.journalapp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class EventController {
    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;

    @GetMapping("/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message) {
        try {
       //  kafkaMessagePublisher.sendMessage(message);
            return ResponseEntity.ok("Message send scesfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
    }
}
