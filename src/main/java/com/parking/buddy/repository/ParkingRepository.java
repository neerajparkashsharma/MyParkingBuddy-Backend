package com.parking.buddy.repository;

import com.parking.buddy.entity.Parking;
import com.parking.buddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ParkingRepository  extends JpaRepository<Parking,Long> {

    Parking findParkingByCameraMacAddress(String cameraMacAddress);
    List<Parking> findByIsActive(boolean active);
    List<Parking> findParkingByHost(User host);
//    List<Parking> findByIsActiveTrue();
//    List<Parking> findByIsAvailableTrue();
//    List<Parking> findByIsBookedTrue();
//    List<Parking> findByIsCancelledTrue();
////    List<Parking> findByHostId(Long hostId);
//    List<Parking> findByParkingLocation(String location);
//    List<Parking> findByLatitudeAndLongitude(String latitude, String longitude);
//    List<Parking> findByIsActiveFalse(); // Find all inactive parkings
//    List<Parking> findByIsAvailableFalse(); // Find all unavailable parkings
//    List<Parking> findByHost(User host); // Find all parkings owned by a specific user
//    List<Parking> findByParkingLocationContaining(String location); //Find all parkings that contain the given location in their name or address
//    List<Parking> findByIsActiveTrueAndIsAvailableTrue(); //Find all active and available parkings
//    List<Parking> findByIsActiveTrueAndIsAvailableTrueAndParkingChargesLessThan(float maxCharges); //Find all active and available parkings with parking charges less than the given value
//    List<Parking> findByLatitudeBetweenAndLongitudeBetween(String minLatitude, String maxLatitude, String minLongitude, String maxLongitude); //Find all parkings whose latitude and longitude fall between the given range
//    List<Parking> findByCreateDateBetween(Date startDate, Date endDate); //Find all parkings created between the given dates
//    List<Parking> findByHostIdAndIsActiveTrue(Long hostId); // Find all active parkings owned by a specific user
//    List<Parking> findByIsBookedTrueAndIsActiveTrueAndCreateDateBefore(Date bookingDate);

}
