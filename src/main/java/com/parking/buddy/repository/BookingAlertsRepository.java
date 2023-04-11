package com.parking.buddy.repository;

import com.parking.buddy.entity.BookingAlerts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingAlertsRepository extends JpaRepository<BookingAlerts, Long> {

    List<BookingAlerts> findByIsActiveTrue();
    List<BookingAlerts> findByCreatedDateBetween(Date startDate, Date endDate);
    List<BookingAlerts> findByMessageContaining(String keyword);
    List<BookingAlerts> findByIsActiveTrueAndMessageContaining(String keyword);
    Optional<BookingAlerts> findById(Long id);
    Optional<BookingAlerts> findByImage(String imageName);


}
