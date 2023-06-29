package com.parking.buddy.controller;

import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.service.BookingAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
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
