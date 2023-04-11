package com.parking.buddy.service;

import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.exception.ResourceAlreadyExistsException;
import com.parking.buddy.repository.ParkingBookingRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.parking.buddy.exception.ResourceNotFoundException;
@Service
public class BookingAlertService{
    @Autowired
    private ParkingBookingRecordsRepository parkingBookingRecordsRepository;

    public List<ParkingBookingRecords> getBookingAlerts() {
        return parkingBookingRecordsRepository.findAll();
    }

    public ParkingBookingRecords getBookingAlertById(long id) throws ResourceNotFoundException {
        return parkingBookingRecordsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking Alert", "id", id));
    }

    public ParkingBookingRecords saveBookingAlert(ParkingBookingRecords parkingBookingRecords) throws ResourceAlreadyExistsException {
        Long id = parkingBookingRecords.getId();
        if (id != null && parkingBookingRecordsRepository.existsById(id)) {
            throw new ResourceAlreadyExistsException("Booking Alert", "id", id);
        }
        return parkingBookingRecordsRepository.save(parkingBookingRecords);
    }


    public void deleteBookingAlert(long id) throws ResourceNotFoundException {
        if (!parkingBookingRecordsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking Alert", "id", id);
        }
        parkingBookingRecordsRepository.deleteById(id);
    }
}
