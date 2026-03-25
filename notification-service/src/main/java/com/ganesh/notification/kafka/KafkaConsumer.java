package com.ganesh.notification.kafka;

import com.ganesh.common.dto.JournalCreatedEventDTO;
import com.ganesh.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final EmailService emailService;

    @KafkaListener(topics = "demo", groupId = "notification-group")
    public void consume(JournalCreatedEventDTO event) {
        log.info("Received event: {}", event);
        emailService.sendJournalCreatedEmail(event.getUserEmail(), event.getEntryTitle(), event.getEntryContent());
    }
}