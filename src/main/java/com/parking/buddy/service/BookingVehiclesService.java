package com.parking.buddy.service;

import com.parking.buddy.entity.BookingVehicles;
import com.parking.buddy.exception.ResourceAlreadyExistsException;
import com.parking.buddy.exception.ResourceNotFoundException;
import com.parking.buddy.repository.BookingVehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookingVehiclesService {

    @Autowired
    private BookingVehiclesRepository bookingVehiclesRepository;

    public List<BookingVehicles> getAllBookingVehicles() {
        return bookingVehiclesRepository.findAll();
    }

    public BookingVehicles getBookingVehiclesById(Long id) {
        Optional<BookingVehicles> bookingVehicles = bookingVehiclesRepository.findById(id);
        if (bookingVehicles.isPresent()) {
            return bookingVehicles.get();
        } else {
            throw new ResourceNotFoundException("BookingVehicles", "id", id);
        }
    }

    public BookingVehicles saveBookingVehicles(BookingVehicles bookingVehicles) {
        try {
            return bookingVehiclesRepository.save(bookingVehicles);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("BookingVehicles", "id", bookingVehicles.getId());
        }
    }

}

