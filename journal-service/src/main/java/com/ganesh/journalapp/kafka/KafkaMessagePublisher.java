package com.ganesh.journalapp.kafka;

import com.ganesh.common.dto.JournalCreatedEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaMessagePublisher {
    private final KafkaTemplate<String, Object> template;

    public void sendMessage(JournalCreatedEventDTO event) {
        CompletableFuture<SendResult<String, Object>> send = template.send("demo", event);
        send.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent event: " + event + " with offset " + result.getRecordMetadata().offset());
            } else {
                System.out.println("Unable to send event: " + event + " due to " + ex.getMessage());
            }
        });
    }
}
