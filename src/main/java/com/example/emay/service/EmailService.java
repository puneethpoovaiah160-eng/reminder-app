package com.example.emay.service;

import com.example.emay.model.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${reminder.recipient.email}")
    private String recipientEmail;

    public void sendReminderEmail(Reminder reminder) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Reminder: " + reminder.getTitle());
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(reminder.getTitle()).append("\n\n");
        if (reminder.getDescription() != null) sb.append(reminder.getDescription()).append("\n\n");
        sb.append("When: ").append(reminder.getReminderTime()).append("\n");
        message.setText(sb.toString());
        mailSender.send(message);
    }

}
