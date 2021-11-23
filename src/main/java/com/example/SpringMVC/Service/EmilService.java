package com.example.SpringMVC.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmilService {
    @Value("${spring.mail.username}")
    private String username;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String email,String RE,String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(RE);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
    }

}
