package com.example.emay.repository;

import com.example.emay.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findByStatusOrderByReminderTimeAsc(String status);

    List<Reminder> findByStatusAndReminderTimeBefore(String status, LocalDateTime time);

}
