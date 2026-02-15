package com.example.emay.service;

import com.example.emay.model.Reminder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${reminder.recipient.email}")
    private String recipientEmail;

    private final String EMAIL_API_URL =
            "https://email-bridge-drf2mwqu8-puneethpoovaiah160-engs-projects.vercel.app/api/send";

    public void sendReminderEmail(Reminder reminder) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("to", recipientEmail);
        body.put("subject", "Reminder: " + reminder.getTitle());

        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(reminder.getTitle()).append("\n\n");
        if (reminder.getDescription() != null) {
            sb.append(reminder.getDescription()).append("\n\n");
        }
        sb.append("When: ").append(reminder.getReminderTime());

        body.put("text", sb.toString());

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(EMAIL_API_URL, request, String.class);
    }
}
