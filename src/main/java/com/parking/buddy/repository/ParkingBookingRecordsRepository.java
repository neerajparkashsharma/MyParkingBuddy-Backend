package com.parking.buddy.repository;

import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.parking.buddy.entity.Parking;
@Repository
public interface ParkingBookingRecordsRepository extends JpaRepository<ParkingBookingRecords,Long> {


    List<ParkingBookingRecords> findAllByCustomer(User u);
    List<ParkingBookingRecords> findByCustomer(User customer);

    List<ParkingBookingRecords> findByParking(Parking parking);
    List<ParkingBookingRecords> findByIsActiveTrue();
    List<ParkingBookingRecords> findByCustomerAndIsActiveTrue(User customer);

    List<ParkingBookingRecords> findByParkingAndIsActiveTrue(Parking parking);

    List<ParkingBookingRecords> findByCustomerAndIsExpiredFalse(User customer);

    List<ParkingBookingRecords> findByParkingAndIsExpiredFalse(Parking parking);




}
