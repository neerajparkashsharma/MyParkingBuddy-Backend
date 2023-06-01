package com.parking.buddy.service;

import com.parking.buddy.entity.BookingVehicles;
import com.parking.buddy.entity.Parking;
import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.exception.ResourceAlreadyExistsException;
import com.parking.buddy.exception.ResourceNotFoundException;
import com.parking.buddy.repository.BookingVehiclesRepository;
import com.parking.buddy.repository.ParkingBookingRecordsRepository;
import com.parking.buddy.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingVehiclesService {

    @Autowired
    private BookingVehiclesRepository bookingVehiclesRepository;
    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private ParkingBookingRecordsRepository parkingBookingRecordsRepository;

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


    public ResponseEntity<?> markCheckIn(Long parkingId, Long userId, String checkInCode, Long bookingId) {
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new ResourceNotFoundException("Parking", "id", parkingId));

        ParkingBookingRecords parkingBookingRecords = parkingBookingRecordsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

        Date currentDateTime = new Date();

        if (parking.getCheckInCode().equals(checkInCode) &&
                parkingBookingRecords.getCustomer().getId()==(userId) &&
                parkingBookingRecords.getParkFromDate().before(currentDateTime) &&
                parkingBookingRecords.getParkToDate().after(currentDateTime) &&
                !parkingBookingRecords.getIsExpired()) {

            BookingVehicles bookingVehicles = new BookingVehicles();
            bookingVehicles.setCheckIn(new Date());
            bookingVehicles.setParkingBookingRecords(parkingBookingRecords);
            bookingVehicles.setAllowed(true);
            bookingVehicles.setCheckInInput(checkInCode);

            // Return a success response with the created BookingVehicles object
            return ResponseEntity.ok(bookingVehicles);
        } else {
            // Return an error response with the appropriate status code and message
            return ResponseEntity.notFound().build();
        }
    }



//    public void checkIn(BookingVehicles booking, parkingId) {
//
//        booking.getParkingBookingRecords().getParking().getCheckInCode().equals(booking.getCheckInInput() ){
//
//
//         booking.setCheckIn(new Date());
//        }
//    }
//
//     public void checkOut(BookingVehicles booking) {
//
//
//        booking.setCheckOut(new Date());
//
//    }



}

