package com.parking.buddy.controller;

import com.parking.buddy.entity.*;
import com.parking.buddy.entity.DTO.ParkingBookingRequest;
import com.parking.buddy.entity.DTO.parkingBookingDTO;
import com.parking.buddy.entity.response.ParkingBookingsList;
import com.parking.buddy.repository.*;
import com.parking.buddy.service.ParkingBookingRecordsService;
import com.parking.buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ParkingBookingRecordsController {
    @Autowired
    private ParkingBookingRecordsService parkingBookingRecordsService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private ParkingRecordsRepository parkingRecordsRepository;
    @Autowired
    private ParkingBookingRecordsRepository parkingBookingRecordsRepository;
    @Autowired
    private BookingDatesRespository bookingDatesRespository;

    @GetMapping("/parkingBookingRecords")
    private List<ParkingBookingRecords> getAllParkingBookingRecords() {
        return parkingBookingRecordsService.getAllParkingBookingRecords();
    }

    @GetMapping("/parkingBookingRecords/{id}")
    private ParkingBookingRecords getParkingBookingRecordsById(@PathVariable long id) {
        return parkingBookingRecordsService.getParkingBookingRecordsById(id);
    }

    @GetMapping("/parkingBookingRecords/customer/{id}")
    private List<ParkingBookingsList> getParkingBookingRecordsByCustomerId(@PathVariable Long id) {
        User u = userService.getUserById(id);

        List<ParkingBookingsList> bookingsList =  new ArrayList<>();


        List<ParkingBookingRecords> bookingRecords = u.getBookingRecords();

        for(ParkingBookingRecords bookingRecord : bookingRecords) {

            ParkingBookingsList booking = new ParkingBookingsList();
            booking.setParkingBookingRecords(bookingRecord);

           List<BookingDates> bookingDates = bookingDatesRespository.findByParkingBookingId(bookingRecord.getId());
            booking.setBookingDates(bookingDates);

            bookingsList.add(booking);

        }


        // Sort the bookingRecords by createdOn in descending order
        bookingsList.sort((r1, r2) -> {
            if (r1.parkingBookingRecords.getCreatedDate() == null && r2.parkingBookingRecords.getCreatedDate() == null) {
                return 0;
            } else if (r1.parkingBookingRecords.getCreatedDate() == null) {
                return 1;
            } else if (r2.parkingBookingRecords.getCreatedDate() == null) {
                return -1;
            }
            return r2.parkingBookingRecords.getCreatedDate().compareTo(r1.parkingBookingRecords.getCreatedDate());
        });

        return bookingsList;
    }


    @GetMapping("/bookings")
    private List<ParkingBookingsList> getBookings() {


        List<ParkingBookingsList> bookingsList =  new ArrayList<>();


        List<ParkingBookingRecords> bookingRecords = parkingBookingRecordsRepository.findAll();

        for(ParkingBookingRecords bookingRecord : bookingRecords) {

            ParkingBookingsList booking = new ParkingBookingsList();
            booking.setParkingBookingRecords(bookingRecord);

            List<BookingDates> bookingDates = bookingDatesRespository.findByParkingBookingId(bookingRecord.getId());
            booking.setBookingDates(bookingDates);

            bookingsList.add(booking);

        }


        // Sort the bookingRecords by createdOn in descending order
        bookingsList.sort((r1, r2) -> {
            if (r1.parkingBookingRecords.getCreatedDate() == null && r2.parkingBookingRecords.getCreatedDate() == null) {
                return 0;
            } else if (r1.parkingBookingRecords.getCreatedDate() == null) {
                return 1;
            } else if (r2.parkingBookingRecords.getCreatedDate() == null) {
                return -1;
            }
            return r2.parkingBookingRecords.getCreatedDate().compareTo(r1.parkingBookingRecords.getCreatedDate());
        });

        return bookingsList;
    }


//    @PostMapping("/parkingbooking/")
//    public ResponseEntity<?> bookingParking(@RequestBody parkingBookingDTO dto) {
//
//        try {
//            boolean isBooked = bookParking(dto);
//            if (isBooked) {
//                return ResponseEntity.ok().build();
//            } else {
//                return ResponseEntity.badRequest().body("Booking failed: Parking spot is already booked for the requested time and date.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Booking failed: " + e.getMessage());
//        }
//    }
//
//    public boolean bookParking(parkingBookingDTO dto) throws ParseException {
//        Optional<Parking> parking = parkingRepository.findById(dto.parkingId);
//        if (!parking.isPresent()) {
//            // parking not found
//            return false;
//        }
//
//        List<ParkingBookingRecords> existingBookings = parkingBookingRecordsRepository.findByParking(parking.get());
//        for (ParkingBookingRecords booking : existingBookings) {
//            Date parkFromDate = java.sql.Date.valueOf(dto.bookingDate);
//
//            // check if booking conflicts with requested time and date
//            if (booking.getFromTime().compareTo(Time.valueOf(dto.bookingEndTime)) < 0
//                    && booking.getToTime().compareTo(Time.valueOf(dto.bookingStartTime)) > 0
//                    && booking.getParkFromDate().compareTo(parkFromDate) <= 0
//                    && booking.getParkToDate().compareTo(parkFromDate) >= 0) {
//                // booking already exists for requested time and date
//                return false;
//            }
//        }
//
//        User customer = userRepository.findById(dto.customerId).orElse(null);
//
//        // create new booking record
//        ParkingBookingRecords booking = new ParkingBookingRecords();
//        booking.setParking(parking.get());
//        booking.setCustomer(customer);
//        booking.setFromTime(Time.valueOf(dto.bookingStartTime));
//        booking.setToTime(Time.valueOf(dto.bookingEndTime));
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date parkDate = dateFormat.parse(dto.bookingDate);
//        booking.setParkFromDate(parkDate);
//        booking.setParkToDate(parkDate);
//
//        Date fromDate = new Date(dto.bookingStartTime);
//        Date toDate = new Date(dto.bookingEndTime);
//
//        long durationInMillis = toDate.getTime() - fromDate.getTime();
//        double durationInHours = (double) durationInMillis / (1000 * 60 * 60); // convert milliseconds to hours
//
//
//
//        booking.setActive(true);
//        booking.setCreatedDate(new Date());
//        booking.setExpired(false);
//        booking.setTotalParkingCharges((float) (durationInHours * parking.get().getParkingCharges()));
//
//        parkingBookingRecordsRepository.save(booking);
//        return true;
//    }
//


    @PostMapping("/book-parking")
    public ResponseEntity<?> bookParking(@RequestBody ParkingBookingRequest booking) {
        return parkingBookingRecordsService.bookParking(booking);
    }

    @GetMapping("/parking_dates_occupied/{parkingId}")
    public List<BookingDates> getParkingDatesOccupied(@PathVariable Long parkingId) {
        return parkingBookingRecordsService.getUnavailableDatesForParking(parkingId);
    }

    @PostMapping("/parkingBookingRecords")
    private String saveParkingBookingRecords(@RequestBody parkingBookingDTO p) {

        try {

//             Optional<User> customer = userRepository.findById(p.customerId);
//             Parking parking = parkingRepository.findById(p.parkingId).orElse(null);
//             ParkingBookingRecords parkingBookingRecords = new ParkingBookingRecords();
//             parkingBookingRecords.setActive(true);
//             parkingBookingRecords.setCustomer(customer.get());
////             parkingBookingRecords.setParkFromDate();
////             parkingBookingRecords.setParkToDate();
//             parkingBookingRecords.setCreatedDate(new Date());
//            LocalTime bookingStartTime = LocalTime.parse(p.bookingStartTime);
//            parkingBookingRecords.setToTime(Time.valueOf(bookingStartTime));
//            LocalTime bookingEndTime = LocalTime.parse(p.bookingEndTime);
//
//            parkingBookingRecords.setFromTime(Time.valueOf(bookingEndTime));
//
//            parkingBookingRecords.setCreatedDate(new Date());
//            parkingBookingRecords.setParking(parking);
//            parkingBookingRecords.setActive(true);
////            parkingRecords.set
//
////             ParkingRecords parkingRecords= parkingRecordsRepository.findBy()
////             if()
////
////
////            if (parking != null && parking.getIsAvailable() && parking.getIsActive()) {
////                // Check if the parking is already booked
////                List<ParkingBookingRecords> bookings = parking.getHost().getBookingRecords();
////                for (ParkingBookingRecords booking : bookings) {
////                    if (booking.getParkFromDate().equals(p.bookingDate)) {
////                         return "This parking is already booked on the selected date";
//////                   }
////                }
////                ParkingBookingRecords parkingBookingRecords = new ParkingBookingRecords();
////                parkingBookingRecords.setParking(parkingRepository.findById(p.parkingId).orElse(null));
////                parkingBookingRecords.setCustomer(userService.getUserById(p.customerId));
////                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
////                Date parkingBookingDate = format.parse(p.bookingDate, new ParsePosition(0));
////                parkingBookingRecords.setActive(true);
////
////                 parkingBookingRecords.setParkFromDate(parkingBookingDate);
////                parkingBookingRecords.setParkToDate(parkingBookingDate);
////                parkingBookingRecords.setCreatedDate(new Date());
////                parkingBookingRecords.setCreatedBy(p.customerId);
////                parking.setIsAvailable(false);
////                parking.setIsBooked(true);
////                ParkingBookingRecords pBR = parkingBookingRecordsRepository.save(parkingBookingRecords);
////            }
////            else{
////                return "Parking is not available";
////            }

            return "Success";
        } catch (Exception e) {
            return "Failed";
        }
    }

    private Date getFormattedDate(String dateStr, String timeStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateStr = dateStr + " " + timeStr + ":00";
        Date date = format.parse(formattedDateStr);
        return date;
    }


    @DeleteMapping("/parkingBookingRecords/{id}")
    private void deleteParkingBookingRecords(@PathVariable long id) {
        parkingBookingRecordsService.deleteParkingBookingRecords(id);
    }

    @GetMapping("/parking/bookings/{id}")
    public ResponseEntity<?> getBookingsByParkingId(@PathVariable Long id) {

        return parkingBookingRecordsService.getBookingsByParkingId(id);
    }


}
