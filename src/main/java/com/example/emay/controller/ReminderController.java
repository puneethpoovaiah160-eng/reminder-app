package com.example.emay.controller;

import com.example.emay.model.Reminder;
import com.example.emay.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderRepository reminderRepository;

    @PostMapping
    public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder) {
        reminder.setStatus("PENDING");
        if (reminder.getCreatedAt() == null) reminder.setCreatedAt(LocalDateTime.now());
        Reminder saved = reminderRepository.save(reminder);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/upcoming")
    public List<Reminder> upcoming() {
        return reminderRepository.findByStatusOrderByReminderTimeAsc("PENDING");
    }

    @GetMapping("/history")
    public List<Reminder> history() {
        return reminderRepository.findByStatusOrderByReminderTimeAsc("COMPLETED");
    }

}
