package com.parking.buddy.repository;

import com.parking.buddy.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findByBookingId(Long appointmentId);
}