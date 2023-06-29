package com.parking.buddy.service;

import java.awt.print.Book;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.parking.buddy.entity.BookingDates;
import com.parking.buddy.entity.DTO.ParkingBookingRequest;
import com.parking.buddy.entity.Parking;
import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.entity.User;
import com.parking.buddy.repository.BookingDatesRespository;
import com.parking.buddy.repository.ParkingBookingRecordsRepository;
import com.parking.buddy.repository.ParkingRepository;
import com.parking.buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.Between;
import org.springframework.format.annotation.DateTimeFormat;
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
    private BookingDatesRespository bookingDatesRespository;

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

    public List<BookingDates> getUnavailableDatesForParking(long parkingId) {
        try {

            Parking parking = parkingRepository.findById(parkingId).orElse(null);

             List<ParkingBookingRecords> parkingBookingRecords = parkingBookingRecordsRepository.findByParking(parking);

                List<BookingDates> bookingDates = new ArrayList<>();

                for(ParkingBookingRecords bookingRecords : parkingBookingRecords){
                    bookingDates.addAll(bookingDatesRespository.findByParkingBookingId(bookingRecords.getId()));
                }

                return bookingDates;

        } catch (Exception e) {
            // Handle the exception
            throw e;
        }
    }

    public ResponseEntity<?> bookParking(ParkingBookingRequest booking) {
        try {


            Optional<Parking> parking = parkingRepository.findById(booking.getParkingId());


            if (!parking.isPresent()) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking spot not found");
            }




           List<BookingDates> unavailableDates = getUnavailableDatesForParking(booking.getParkingId());
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

            if(unavailableDates.size() > 0){
                for(BookingDates bookingDates : unavailableDates){
                    for(String bookingDate : booking.bookingDates){
                        String bookingDateFormatted = dateFormatter.format(bookingDates.getBookingDate());

                        if(bookingDateFormatted.equals(bookingDate)){
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking spot not available for selected dates");
                        }
                    }
                }
            }



            ParkingBookingRecords parkingBookingRecords = new ParkingBookingRecords();


            Optional<User> customer = userRepository.findById(booking.getCustomerId());
            if (!customer.isPresent()) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer not found");
            }

            float price = parking.get().getParkingCharges();




            parkingBookingRecords.setCustomer(customer.get());
            parkingBookingRecords.setParking(parking.get());
            parkingBookingRecords.setIsActive(true);
            parkingBookingRecords.setCreatedDate(new Date());
            parkingBookingRecords.setCreatedBy(customer.get().getId());
            parkingBookingRecords.setTotalParkingCharges(price*24*booking.bookingDates.size());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            List<Date> dateObjects = new ArrayList<>();

            try {
                for (String dateString : booking.bookingDates) {
                    Date dateObject = formatter.parse(dateString);
                    dateObjects.add(dateObject);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            ParkingBookingRecords bookingRecords = parkingBookingRecordsRepository.save(parkingBookingRecords);
            for (Date bookingDate : dateObjects) {
                BookingDates bookingDates = new BookingDates();
                bookingDates.setBookingDate(bookingDate);
                bookingDates.setParkingBookingId(bookingRecords.getId());

                bookingDatesRespository.save(bookingDates);

            }


                return ResponseEntity.status(HttpStatus.OK).body("Parking Booked Successfully");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//    public ResponseEntity<?> bookParking(ParkingBookingRequest booking) {
//        try {
//
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//            Date parkFromDate = dateFormat.parse(booking.getBookingFromDateTime());
//            Date parkToDate = dateFormat.parse(booking.getBookingToDateTime());
//            LocalDate fromDate = parkFromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalDate toDate = parkToDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//// Calculate the duration between the two dates
//            Duration duration = Duration.between(fromDate.atStartOfDay(), toDate.atStartOfDay());
//
//// Get the number of days
//            long days = duration.toDays() + 1;
//
//            // Clone the date objects
//            Date fromDateClone = (Date) parkFromDate.clone();
//            Date toDateClone = (Date) parkToDate.clone();
//
//// Remove the date portion from the original variables
//            parkFromDate.setYear(0);
//            parkFromDate.setMonth(0);
//            parkFromDate.setDate(0);
//            parkToDate.setYear(0);
//            parkToDate.setMonth(0);
//            parkToDate.setDate(0);
//
//// Get the time values in milliseconds from the cloned variables
//            long parkFromTimeInMillis = parkFromDate.getTime(); // HH:mm:ss
//            long parkToTimeInMillis = parkToDate.getTime(); // HH:mm:ss
//
//// Calculate the duration in milliseconds and convert to hours
//            long durationInMillis = parkToTimeInMillis - parkFromTimeInMillis;
//            long hours = TimeUnit.MILLISECONDS.toHours(durationInMillis);
//
//            // days
//
//
//            if (hours < 1) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking duration should be minimum 1 hour");
//            }
//
//            if (parkFromDate.after(parkToDate)) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking start date should be before end date");
//            }
//
//
//
//
//            Optional<Parking> parking = parkingRepository.findById(booking.getParkingId());
//
//
//            if (!parking.isPresent()) {
//
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking spot not found");
//            }
//
//            List<ParkingBookingRecords> existingBookings = parkingBookingRecordsRepository.findByParking(parking.get());
//
//
//            for (ParkingBookingRecords bookingRecord : existingBookings) {
//                if (bookingRecord.getIsExpired() == null || !bookingRecord.getIsExpired()) {
//                    boolean condition1 = fromDateClone.after(bookingRecord.getParkFromDate()) && fromDateClone.before(bookingRecord.getParkToDate());
//                    boolean condition2 = toDateClone.after(bookingRecord.getParkFromDate()) && toDateClone.before(bookingRecord.getParkToDate());
//                    boolean condition3 = toDateClone.equals(bookingRecord.getParkToDate()) || fromDateClone.equals(bookingRecord.getParkFromDate());
//
//                    if (condition1 || condition2 || condition3) {
//                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking spot already booked for the selected time slot");
//                    }
//                }
//            }
//
//
//            ParkingBookingRecords parkingBookingRecords = new ParkingBookingRecords();
//
//
//            Optional<User> customer = userRepository.findById(booking.getCustomerId());
//            if (!customer.isPresent()) {
//
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer not found");
//            }
//
//            float price = parking.get().getParkingCharges();
//
//            parkingBookingRecords.setCustomer(customer.get());
//            parkingBookingRecords.setParking(parking.get());
//            parkingBookingRecords.setParkFromDate(fromDateClone);
//            parkingBookingRecords.setParkToDate(toDateClone);
//            parkingBookingRecords.setTotalParkingCharges(price * hours * days);
//            parkingBookingRecords.setIsActive(true);
//            parkingBookingRecords.setCreatedDate(new Date());
//            parkingBookingRecords.setCreatedBy(customer.get().getId());
//
//
//            parkingBookingRecordsRepository.save(parkingBookingRecords);
//
//            return ResponseEntity.status(HttpStatus.OK).body("Parking Booked Successfully");
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


    public ResponseEntity<?> getBookingsByParkingId(Long id) {
        try {

            Optional<Parking> parking = parkingRepository.findById(id);

            if (!parking.isPresent()) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking spot not found");
            }

            List<ParkingBookingRecords> existingBookings = parkingBookingRecordsRepository.findByParking(parking.get());


            existingBookings.sort((r1, r2) -> {
                if (r1.getCreatedDate() == null && r2.getCreatedDate() == null) {
                    return 0;
                } else if (r1.getCreatedDate() == null) {
                    return 1;
                } else if (r2.getCreatedDate() == null) {
                    return -1;
                }

                boolean r1Expired = r1.getIsExpired()  == null ? false : r1.getIsExpired();
                boolean r2Expired = r2.getIsExpired() == null ? false : r2.getIsExpired();

                if (r1Expired && !r2Expired) {
                    return 1;
                } else if (!r1Expired && r2Expired) {
                    return -1;
                }

                int dateComparison = r2.getCreatedDate().compareTo(r1.getCreatedDate());

                // Sort by isExpired
                if (dateComparison == 0) {
                    if (!r1Expired && r2Expired) {
                        return -1;
                    }
                }

                return dateComparison;
            });


            return ResponseEntity.status(HttpStatus.OK).body(existingBookings);

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}
