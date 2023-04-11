package com.parking.buddy.repository;

import com.parking.buddy.entity.BookingVehicles;
import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.entity.User;
import com.parking.buddy.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface BookingVehiclesRepository  extends JpaRepository<BookingVehicles, Long> {
    List<BookingVehicles> findByParkingBookingRecords(ParkingBookingRecords parkingBookingRecords);

    List<BookingVehicles> findByVehicle(Vehicle vehicle);

    List<BookingVehicles> findByIsAllowedTrue();

    List<BookingVehicles> findByCheckInBetween(Date startDate, Date endDate);

    List<BookingVehicles> findByCheckOutIsNull();

    List<BookingVehicles> findByParkingBookingRecordsCustomer(User customer);

    List<BookingVehicles> findByParkingBookingRecordsParkFromDateBeforeAndCheckOutIsNull(Date date);


}
