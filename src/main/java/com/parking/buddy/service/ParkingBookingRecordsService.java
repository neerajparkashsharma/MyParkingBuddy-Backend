package com.parking.buddy.service;
import java.util.Date;
import java.util.List;

import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.entity.User;
import com.parking.buddy.repository.ParkingBookingRecordsRepository;
import com.parking.buddy.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingBookingRecordsService {

    @Autowired
    private ParkingBookingRecordsRepository parkingBookingRecordsRepository;

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

}
