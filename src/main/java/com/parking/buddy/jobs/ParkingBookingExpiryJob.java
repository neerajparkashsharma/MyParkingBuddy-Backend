package com.parking.buddy.jobs;

import com.parking.buddy.entity.BookingAlerts;
import com.parking.buddy.entity.BookingDates;
import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ParkingBookingExpiryJob {

    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private ParkingBookingRecordsRepository parkingBookingRecordsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingDatesRespository bookingDatesRespository;

    @Autowired
    private BookingAlertsRepository bookingAlertsRepository;

    @Scheduled(fixedRate = 6000)
    public void checkParkingBookingStatus() {
        try {
            List<BookingDates> bookingDatesList = bookingDatesRespository.findAll();

            Map<Long, List<BookingDates>> bookingDatesMap = bookingDatesList.stream()
                    .collect(Collectors.groupingBy(BookingDates::getParkingBookingId));

            for (Map.Entry<Long, List<BookingDates>> entry : bookingDatesMap.entrySet()) {
                Long parkingBookingId = entry.getKey();
                List<BookingDates> datesList = entry.getValue();

                 String pattern = "dd-MM-yyyy";
                String currentDate =new SimpleDateFormat(pattern).format(new Date());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                boolean allDatesArePassed = datesList.stream()
                        .allMatch(bookingDates -> {
                            try {
                                Date bookingDate = simpleDateFormat.parse(simpleDateFormat.format(bookingDates.getBookingDate()));
                                Date current = simpleDateFormat.parse(currentDate);
                                return bookingDate.before(current);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false; // Handle the exception appropriately in your application
                            }
                        });

                if (allDatesArePassed) {
                    parkingBookingRecordsRepository.findById(parkingBookingId).ifPresent(parkingBookingRecords -> {
                        if ( parkingBookingRecords.getIsExpired() == null) {
                            parkingBookingRecords.setIsExpired(true);
                            parkingBookingRecordsRepository.save(parkingBookingRecords);


                            BookingAlerts bookingAlerts = new BookingAlerts();

                            String message = "Parking Slot Booking"
                                    + " for " + parkingBookingRecordsRepository.findById(parkingBookingId)
                                    .map(parkingBookingRecords1 -> parkingBookingRecords1.getParking().getParkingLocation())
                                    .orElse("Unknown Location")
                                    + " has expired."
                                    + "Dates were " ;
                            bookingAlerts.setMessage(message);
                            bookingAlerts.setCreatedDate(new Date());
                            bookingAlerts.setParkingBookingRecords(parkingBookingRecordsRepository.findById(parkingBookingId).orElse(null));
                            bookingAlerts.setActive(true);
                            bookingAlertsRepository.save(bookingAlerts);

                        }
                    });

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
