package com.parking.buddy.jobs;

import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.repository.ParkingBookingRecordsRepository;
import com.parking.buddy.repository.ParkingRepository;
import com.parking.buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ParkingBookingExpiryJob {

    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private ParkingBookingRecordsRepository parkingBookingRecordsRepository;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate =  6000)
    public void checkParkingBookingStatus() {
        try {

            System.out.println("Checking parking booking status...");
            List<ParkingBookingRecords> parkingBookingRecords = parkingBookingRecordsRepository.findAll();
            for (ParkingBookingRecords bookingRecord : parkingBookingRecords) {
                if (bookingRecord.getIsExpired() == null || !bookingRecord.getIsExpired()) {
                    if (bookingRecord.getParkToDate().before(new Date())) {
                        bookingRecord.setIsExpired(true);
                        bookingRecord.setIsActive(false);
                        System.out.println("............................................");
                        System.out.println(">>> Parking booking expired for booking id: " + bookingRecord.getId());
                        System.out.println("............................................");

                        parkingBookingRecordsRepository.save(bookingRecord);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
