package com.parking.buddy.controller;

import com.parking.buddy.entity.BookingVehicles;
import com.parking.buddy.service.BookingVehiclesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    private BookingVehicles getBookingVehiclesById(Long id){
        return bookingVehiclesService.getBookingVehiclesById(id);
    }

    @PostMapping("/bookingVehicles")
    private BookingVehicles saveBookingVehicles(@RequestBody BookingVehicles bookingVehicles){
        return bookingVehiclesService.saveBookingVehicles(bookingVehicles);
    }




}
