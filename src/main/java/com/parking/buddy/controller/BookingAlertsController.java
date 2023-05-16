package com.parking.buddy.controller;

import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.service.BookingAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class BookingAlertsController {
    @Autowired
    private BookingAlertService bookingAlertsService;

    @GetMapping("/bookingAlerts")
    private List<ParkingBookingRecords> getAllBookingAlerts() {
        return bookingAlertsService.getBookingAlerts();
    }
    @PostMapping("/bookingAlerts")
    private ParkingBookingRecords saveBookingAlerts(@RequestBody ParkingBookingRecords parkingBookingRecords) {
        return bookingAlertsService.saveBookingAlert(parkingBookingRecords);
    }

    @GetMapping("/bookingAlerts/{id}")
    private ParkingBookingRecords getBookingAlertsById(@PathVariable long id) {
        return bookingAlertsService.getBookingAlertById(id);
    }
}
