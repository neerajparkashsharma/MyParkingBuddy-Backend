package com.parking.buddy.repository;

import com.parking.buddy.entity.BookingDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  java.util.List;
@Repository
public interface BookingDatesRespository extends JpaRepository<BookingDates,Long> {

    List<BookingDates> findByParkingBookingId(Long parkingBookingId);
}
