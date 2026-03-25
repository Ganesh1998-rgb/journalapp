package com.ganesh.notification.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendJournalCreatedEmail(String to, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("New Journal Entry Created: " + title);
        message.setText("Your new journal entry has been created.\n\nTitle: " + title + "\nContent: " + content);
        mailSender.send(message);
    }
}

