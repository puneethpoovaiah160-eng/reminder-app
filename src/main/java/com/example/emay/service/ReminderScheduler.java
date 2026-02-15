package com.example.emay.service;

import com.example.emay.model.Reminder;
import com.example.emay.repository.ReminderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ReminderScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ReminderScheduler.class);

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 60000)
    public void checkReminders() {
        // Get current time in Asia/Kolkata timezone
        ZonedDateTime nowIndia = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        LocalDateTime now = nowIndia.toLocalDateTime();
        
        logger.info("Checking for pending reminders before: {} (India time)", now);
        List<Reminder> due = reminderRepository.findByStatusAndReminderTimeBefore("PENDING", now);
        logger.info("Found {} reminders due", due.size());
        
        for (Reminder r : due) {
            try {
                logger.info("Sending reminder email for id={}, title={}", r.getId(), r.getTitle());
                emailService.sendReminderEmail(r);
                r.setStatus("COMPLETED");
                r.setCompletedAt(LocalDateTime.now());
                reminderRepository.save(r);
                logger.info("Successfully processed reminder id={}", r.getId());
            } catch (Exception ex) {
                logger.error("Failed to send reminder for id={}: {}", r.getId(), ex.getMessage(), ex);
            }
        }
    }

}