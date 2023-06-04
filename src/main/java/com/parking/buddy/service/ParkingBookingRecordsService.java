package com.parking.buddy.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.parking.buddy.entity.DTO.ParkingBookingRequest;
import com.parking.buddy.entity.Parking;
import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.entity.User;
import com.parking.buddy.repository.ParkingBookingRecordsRepository;
import com.parking.buddy.repository.ParkingRepository;
import com.parking.buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.Between;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ParkingBookingRecordsService {

    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private ParkingBookingRecordsRepository parkingBookingRecordsRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ParkingBookingRecords> getAllParkingBookingRecords() {
        try {
            return parkingBookingRecordsRepository.findAll();
        } catch (Exception e) {
            // Handle the exception
            throw e;
        }
    }

    public ParkingBookingRecords getParkingBookingRecordsById(long id) {
        try {
            return parkingBookingRecordsRepository.findById(id).orElse(null);
        } catch (Exception e) {
            // Handle the exception
            throw e;
        }
    }

    public ParkingBookingRecords saveParkingBookingRecords(ParkingBookingRecords parkingBookingRecords) {
        try {
            parkingBookingRecords.setCreatedDate(new Date());
            parkingBookingRecords.setCreatedBy(parkingBookingRecords.getCustomer().getId());
            return parkingBookingRecordsRepository.save(parkingBookingRecords);
        } catch (Exception e) {
            // Handle the exception
            throw e;
        }
    }

    public List<ParkingBookingRecords> getAllParkingBookingRecordsByCustomerId(User u) {
        try {
            return parkingBookingRecordsRepository.findAllByCustomer(u);
        } catch (Exception e) {
            // Handle the exception
            throw e;
        }
    }

    public void deleteParkingBookingRecords(long id) {
        try {
            parkingBookingRecordsRepository.deleteById(id);
        } catch (Exception e) {
            // Handle the exception
            throw e;
        }
    }

    public void deleteAllParkingBookingRecords() {
        try {
            parkingBookingRecordsRepository.deleteAll();
        } catch (Exception e) {
            // Handle the exception
            throw e;
        }
    }


    public ResponseEntity<?> bookParking(ParkingBookingRequest booking) {
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date parkFromDate = dateFormat.parse(booking.getBookingFromDateTime());
            Date parkToDate = dateFormat.parse(booking.getBookingFromDateTime());
            if(!booking.getBookingToDateTime().equals("Invalid date"))
                parkToDate = dateFormat.parse(booking.getBookingToDateTime());

            long durationInMillis = parkToDate.getTime() - parkFromDate.getTime();
            long hours = TimeUnit.MILLISECONDS.toHours(durationInMillis);


            Optional<Parking> parking = parkingRepository.findById(booking.getParkingId());


            if (!parking.isPresent()) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking spot not found");
            }

            List<ParkingBookingRecords> existingBookings = parkingBookingRecordsRepository.findByParking(parking.get());



            for (ParkingBookingRecords bookingRecord : existingBookings) {
                if (bookingRecord.getIsExpired() == null || !bookingRecord.getIsExpired()) {
                    boolean condition1 = parkFromDate.after(bookingRecord.getParkFromDate()) && parkFromDate.before(bookingRecord.getParkToDate());
                    boolean condition2 = parkToDate.after(bookingRecord.getParkFromDate()) && parkToDate.before(bookingRecord.getParkToDate());
                    boolean condition3 = parkToDate.equals(bookingRecord.getParkToDate()) || parkFromDate.equals(bookingRecord.getParkFromDate());

                    if (condition1 || condition2 || condition3){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking spot already booked for the selected time slot");
                    }
                }
            }



        ParkingBookingRecords parkingBookingRecords = new ParkingBookingRecords();



            Optional<User> customer = userRepository.findById(booking.getCustomerId());
            if (!customer.isPresent()) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer not found");
            }

           float price=  parking.get().getParkingCharges();

            parkingBookingRecords.setCustomer(customer.get());
            parkingBookingRecords.setParking(parking.get());
            parkingBookingRecords.setParkFromDate(parkFromDate);
            parkingBookingRecords.setParkToDate(parkToDate);
            parkingBookingRecords.setTotalParkingCharges(price*hours);
            parkingBookingRecords.setIsActive(true);
            parkingBookingRecords.setCreatedDate(new Date());
            parkingBookingRecords.setCreatedBy(customer.get().getId());





            parkingBookingRecordsRepository.save(parkingBookingRecords);

            return ResponseEntity.status(HttpStatus.OK).body("Parking Booked Successfully");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public ResponseEntity<?> getBookingsByParkingId(Long id) {
        try {

            Optional<Parking> parking = parkingRepository.findById(id);

            if (!parking.isPresent()) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking spot not found");
            }

            List<ParkingBookingRecords> existingBookings = parkingBookingRecordsRepository.findByParking(parking.get());



            // Sort the bookingRecords by createdOn in descending order
                existingBookings.sort((r1, r2) -> {
                if (r1.getCreatedDate() == null && r2.getCreatedDate() == null) {
                    return 0;
                } else if (r1.getCreatedDate() == null) {
                    return 1;
                } else if (r2.getCreatedDate() == null) {
                    return -1;
                }
                return r2.getCreatedDate().compareTo(r1.getCreatedDate());
            });
            return ResponseEntity.status(HttpStatus.OK).body(existingBookings);

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}
