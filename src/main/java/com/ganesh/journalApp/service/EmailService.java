package com.ganesh.journalApp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {


private final  JavaMailSender javaMailSender;

    public void send(String to,String subject,String body){
        try{
            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom("xxxxxxxxxxxxxx");
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(body);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
          log.error("Error  while sending email: "+e);
        }

    }
}
