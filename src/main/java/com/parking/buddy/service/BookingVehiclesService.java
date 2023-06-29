package com.parking.buddy.service;

import com.parking.buddy.entity.BookingDates;
import com.parking.buddy.entity.BookingVehicles;
import com.parking.buddy.entity.Parking;
import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.entity.request.ParkingCheckInRequest;
import com.parking.buddy.entity.request.ParkingCheckOutRequest;
import com.parking.buddy.exception.ResourceAlreadyExistsException;
import com.parking.buddy.exception.ResourceNotFoundException;
import com.parking.buddy.repository.BookingDatesRespository;
import com.parking.buddy.repository.BookingVehiclesRepository;
import com.parking.buddy.repository.ParkingBookingRecordsRepository;
import com.parking.buddy.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
    private BookingDatesRespository bookingDatesRespository;

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

    public List<BookingVehicles> getAllBookingVehicles(Long bookingId) {
       Optional<ParkingBookingRecords> parkingBookingRecords= parkingBookingRecordsRepository.findById(bookingId);
        return bookingVehiclesRepository.findBookingVehiclesByParkingBookingRecordsOrderByIdDesc(parkingBookingRecords.get());
    }


    public ResponseEntity<?> markCheckIn(ParkingCheckInRequest parkingCheckInRequest) {
        Parking parking = parkingRepository.findById(parkingCheckInRequest.getParkingId())
                .orElseThrow(() -> new ResourceNotFoundException("Parking", "id", parkingCheckInRequest.getParkingId()));

        ParkingBookingRecords parkingBookingRecords = parkingBookingRecordsRepository.findById(parkingCheckInRequest.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", parkingCheckInRequest.getBookingId()));


        boolean isCheckInCodeValid;
        boolean isUserIdMatched;
        boolean isBookingNotExpired;
        boolean isCheckInDateValid = false;

        try{
             isCheckInCodeValid = parking.getCheckInCode().equals(parkingCheckInRequest.getCheckInCode());
             isUserIdMatched = parkingBookingRecords.getCustomer().getId() == parkingCheckInRequest.getUserId();
             isBookingNotExpired = !(parkingBookingRecords.getIsExpired() == null ? false: parkingBookingRecords.getIsExpired());

          List<BookingDates> bookingDates=  bookingDatesRespository.findByParkingBookingId(parkingCheckInRequest.getBookingId());

          for (BookingDates bookingDate : bookingDates){
              if(bookingDate.getBookingDate().equals(new Date())){
                    isCheckInDateValid = true;
                    break;
              }
          }
        }

        catch (Exception e){
            System.out.println("Error in checkin");
            return ResponseEntity.internalServerError().build();
        }

        if (isCheckInCodeValid && isUserIdMatched  && isBookingNotExpired && isCheckInDateValid) {

            BookingVehicles bookingVehicles = new BookingVehicles();
            bookingVehicles.setCheckIn(new Date());
            bookingVehicles.setParkingBookingRecords(parkingBookingRecords);
            bookingVehicles.setAllowed(true);

            bookingVehicles.setCheckInInput(parkingCheckInRequest.getCheckInCode());
            bookingVehicles.setCustomerId(parkingCheckInRequest.getUserId());
            bookingVehiclesRepository.save(bookingVehicles);


            try {
                String url = "http://localhost:8000/video_feed";
                String requestData = "user_id=" + parkingCheckInRequest.getUserId() + "&parking_id=" + parkingCheckInRequest.getParkingId();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<StreamingResponseBody> response = restTemplate.getForEntity(url + "?" + requestData, StreamingResponseBody.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Video feed started");
                } else {
                    System.out.println("Video feed failed");
                }
            }
            catch (Exception e){
                System.out.println("Error in video feed");
            }


            // Return a success response with the created BookingVehicles object
            return ResponseEntity.ok(bookingVehicles);
        } else {
            // Return an error response with the appropriate status code and message
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<?> markCheckOut(ParkingCheckOutRequest parkingCheckOutRequest) {
        Long userId = parkingCheckOutRequest.getUserId();
        String checkOutCode = parkingCheckOutRequest.getCheckOutCode();

       Optional<ParkingBookingRecords> parkingBookingRecord= parkingBookingRecordsRepository.findById(parkingCheckOutRequest.getBookingId());

        // Find the last check-in for the user
        BookingVehicles lastCheckIn = bookingVehiclesRepository.findFirstByCustomerIdAndParkingBookingRecordsAndCheckOutIsNullOrderByCheckInDesc(userId, parkingBookingRecord.get());

        if (lastCheckIn != null) {
            ParkingBookingRecords parkingBookingRecords = lastCheckIn.getParkingBookingRecords();
            Parking parking = parkingBookingRecords.getParking();

            if (parking.getCheckOutCode().equals(checkOutCode)) {
                lastCheckIn.setCheckOut(new Date());
                lastCheckIn.setCheckOutInput(checkOutCode);
                bookingVehiclesRepository.save(lastCheckIn);

                return ResponseEntity.ok(lastCheckIn);
            }
        }

        return ResponseEntity.notFound().build();
    }






//    public ResponseEntity<?> markCheckOut(Long parkingId, Long userId, String checkOutCode, Long bookingId) {
//        Parking parking = parkingRepository.findById(parkingId)
//                .orElseThrow(() -> new ResourceNotFoundException("Parking", "id", parkingId));
//
//        ParkingBookingRecords parkingBookingRecords = parkingBookingRecordsRepository.findById(bookingId)
//                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));
//
//
//
//        Date currentDateTime = new Date();
//
//        if (parking.getCheckOutCode().equals(checkOutCode) &&
//                parkingBookingRecords.getCustomer().getId()==(userId) &&
//                parkingBookingRecords.getParkFromDate().before(currentDateTime) &&
//                parkingBookingRecords.getParkToDate().after(currentDateTime) &&
//                !parkingBookingRecords.getIsExpired()) {
//
//            BookingVehicles bookingVehicles = new BookingVehicles();
//
//            bookingVehicles.setParkingBookingRecords(parkingBookingRecords);
//            bookingVehicles.setAllowed(true);
//            bookingVehicles.setCheckOut(new Date());
//            bookingVehicles.setCheckOutInput(checkOutCode);
//
//            // Return a success response with the created BookingVehicles object
//            return ResponseEntity.ok(bookingVehicles);
//        } else {
//            // Return an error response with the appropriate status code and message
//            return ResponseEntity.notFound().build();
//        }
//    }




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

