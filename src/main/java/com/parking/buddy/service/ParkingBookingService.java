package com.parking.buddy.service;


import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.repository.ParkingBookingRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ParkingBookingService {

    @Autowired
    private ParkingBookingRecordsRepository parkingBookingRecordsRepository;

    public List<ParkingBookingRecords> getAllParkingBookingRecords() {
        try {
            return parkingBookingRecordsRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
}
