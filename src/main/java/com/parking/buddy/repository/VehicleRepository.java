package com.parking.buddy.repository;

import com.parking.buddy.entity.BookingVehicles;
import com.parking.buddy.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Find all vehicles by number plate
    List<Vehicle> findAllByNumberPlate(String numberPlate);

    // Find vehicle by number plate
    Vehicle findByNumberPlate(String numberPlate);

    // Find all vehicles by created date
    List<Vehicle> findAllByCreatedDate(Date createdDate);

    // Find all vehicles by updated date
    List<Vehicle> findAllByUpdatedDate(Date updatedDate);

    // Find all vehicles by created by
    List<Vehicle> findAllByCreatedBy(Long createdBy);

    // Find all vehicles by updated by
    List<Vehicle> findAllByUpdatedBy(Long updatedBy);

    // Find all vehicles by is active status
    List<Vehicle> findAllByIsActive(Boolean isActive);

    // Find all vehicles by booking vehicles
//    List<Vehicle> findAllByBookingVehicles(List<BookingVehicles> bookingVehicles);
}
