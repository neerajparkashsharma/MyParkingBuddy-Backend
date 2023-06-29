package com.parking.buddy.controller;

import com.parking.buddy.entity.BookingVehicles;
import com.parking.buddy.entity.request.ParkingCheckInRequest;
import com.parking.buddy.entity.request.ParkingCheckOutRequest;
import com.parking.buddy.service.BookingVehiclesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import  java.util.List;

@RestController
public class BookingVehiclesController {
    @Autowired
    private BookingVehiclesService bookingVehiclesService;
    @GetMapping("/bookingVehicles")
    private  List<BookingVehicles> getAllBookingVehicles(){
        return bookingVehiclesService.getAllBookingVehicles();
    }
    @GetMapping("/bookingVehicles/{id}")
    private BookingVehicles getBookingVehiclesById( @PathVariable Long id){
        return bookingVehiclesService.getBookingVehiclesById(id);
    }
    @PostMapping("/bookingVehicles")
    private BookingVehicles saveBookingVehicles(@RequestBody BookingVehicles bookingVehicles){
        return bookingVehiclesService.saveBookingVehicles(bookingVehicles);
    }

    @PostMapping("/booking/checkin")
    public ResponseEntity<?> checkIn(@RequestBody ParkingCheckInRequest parkingCheckInRequest) throws IOException {
        return bookingVehiclesService.markCheckIn(parkingCheckInRequest);
    }
    @PostMapping("/booking/checkout")
    public ResponseEntity<?> checkOut(@RequestBody ParkingCheckOutRequest parkingCheckOutRequest) {
        return bookingVehiclesService.markCheckOut(parkingCheckOutRequest);
    }

    @GetMapping("/bookingVehicles/all/{bookingid}")
    private List<BookingVehicles> getBookingVehiclesByBookingId(@PathVariable Long bookingid){
        return bookingVehiclesService.getAllBookingVehicles(bookingid);
    }
}
