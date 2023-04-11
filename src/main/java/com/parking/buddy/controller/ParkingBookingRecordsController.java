package com.parking.buddy.controller;

import com.parking.buddy.entity.*;
import com.parking.buddy.entity.DTO.parkingBookingDTO;
import com.parking.buddy.repository.ParkingBookingRecordsRepository;
import com.parking.buddy.repository.ParkingRepository;
import com.parking.buddy.service.ParkingBookingRecordsService;
import com.parking.buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ParkingBookingRecordsController {
    @Autowired
    private ParkingBookingRecordsService parkingBookingRecordsService;
    @Autowired
    private UserService userService;
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private ParkingBookingRecordsRepository parkingBookingRecordsRepository;

    @GetMapping("/parkingBookingRecords")
    private List<ParkingBookingRecords> getAllParkingBookingRecords() {
        return parkingBookingRecordsService.getAllParkingBookingRecords();
    }
    @GetMapping("/parkingBookingRecords/{id}")
    private ParkingBookingRecords getParkingBookingRecordsById(@PathVariable long id) {
        return parkingBookingRecordsService.getParkingBookingRecordsById(id);
    }
    @GetMapping("/parkingBookingRecords/customer/{id}")
    private List<ParkingBookingRecords> getParkingBookingRecordsByCustomerId(@PathVariable Long id) {
        User u = userService.getUserById(id);
        return u.getBookingRecords();
    }
    @PostMapping("/parkingBookingRecords")
    private String saveParkingBookingRecords(@RequestBody parkingBookingDTO p) {

        try {
             Parking parking = parkingRepository.findById(p.parkingId).orElse(null);
            if(parking.getIsAvailable() == true && parking.getIsActive() == true){
                ParkingBookingRecords parkingBookingRecords = new ParkingBookingRecords();
                parkingBookingRecords.setParking(parkingRepository.findById(p.parkingId).orElse(null));
                parkingBookingRecords.setCustomer(userService.getUserById(p.customerId));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date parkingBookingDate = format.parse(p.bookingDate, new ParsePosition(0));
                parkingBookingRecords.setActive(true);
                parkingBookingRecords.setParkFromDate(parkingBookingDate);
                parkingBookingRecords.setParkToDate(parkingBookingDate);
                parkingBookingRecords.setCreatedDate(new Date());
                parkingBookingRecords.setCreatedBy(p.customerId);
                parking.setIsAvailable(false);
                parking.setIsBooked(true);
                ParkingBookingRecords pBR = parkingBookingRecordsRepository.save(parkingBookingRecords);
            }
            else{
                return "Parking is not available";
            }


            return  "Success";
        } catch (Exception e)
        {
            return "Failed";
        }
    }
    @DeleteMapping("/parkingBookingRecords/{id}")
    private void deleteParkingBookingRecords(@PathVariable long id) {
        parkingBookingRecordsService.deleteParkingBookingRecords(id);
    }

}
