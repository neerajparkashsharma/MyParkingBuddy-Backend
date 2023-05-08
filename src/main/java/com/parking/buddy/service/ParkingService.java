package com.parking.buddy.service;

import com.parking.buddy.entity.DTO.ParkingDTO;
import com.parking.buddy.entity.Parking;
import com.parking.buddy.entity.User;
import com.parking.buddy.repository.ParkingRepository;
import com.parking.buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
@Service
public class ParkingService {

    @Autowired
    private ParkingRepository parkingRepo;
    @Autowired
    private UserRepository userRepository;

    public List<Parking> getAllParking() {
        return parkingRepo.findByIsActive(true);
    }

    public Parking getParkingByCameraMacAddress(String macAddress) {
        return parkingRepo.findParkingByCameraMacAddress(macAddress);
    }

    public List<Parking> getParkingByHostId(Long hostId) {
        User host = userRepository.findById(hostId).orElse(null);
        return parkingRepo.findParkingByHost(host);
    }
    public Parking getParkingById(Long id) {
        return parkingRepo.findById(id).orElse(null);
    }

    public String saveParking(ParkingDTO parking) {

        try {
            Parking parkingToSave = new Parking();
            parkingToSave.setCameraMacAddress(parking.getCameraMACAddress());
            parkingToSave.setIsActive(true);
            parkingToSave.setCreatedBy(parking.getHostId());
            parkingToSave.setCreateDate(new Date());
            parkingToSave.setParkingLocation(parking.getParkingLocation());
            parkingToSave.setParkingCharges(parking.getParkingCharges());
            parkingToSave.setCheckInCode(parking.getCheckInCode());
            parkingToSave.setCheckOutCode(parking.getCheckOutCode());
            parkingToSave.setCameraIpAddress(parking.getCameraIP());
            parkingToSave.setIsAvailable(true);
            userRepository.findById(parking.getHostId()).ifPresent(parkingToSave::setHost);
            parkingRepo.save(parkingToSave);

            return "Parking saved successfully";
        }
        catch(Exception e) {
            e.printStackTrace();
//            return "Error";
            throw e;
        }


    }

    public Parking updateParking(Parking parking) {
        Parking parkingToUpdate = parkingRepo.findById(parking.getId()).orElse(null);
        if (parkingToUpdate != null) {
            parkingToUpdate.setCreateDate(parking.getCreateDate());
            parkingToUpdate.setUpdatedDate(new Date());
            parkingToUpdate.setCreatedBy(parking.getCreatedBy());
            parkingToUpdate.setUpdatedBy(parking.getUpdatedBy());
            parkingToUpdate.setIsActive(parking.getIsActive());
            parkingToUpdate.setParkingLocation(parking.getParkingLocation());
            parkingToUpdate.setParkingCharges(parking.getParkingCharges());
            parkingToUpdate.setParkingImage(parking.getParkingImage());
            parkingToUpdate.setIsBooked(parking.getIsBooked());
            parkingToUpdate.setIsCancelled(parking.getIsCancelled());
            parkingToUpdate.setIsAvailable(parking.getIsAvailable());
            parkingToUpdate.setDescription(parking.getDescription());
            parkingToUpdate.setLatitude(parking.getLatitude());
            parkingToUpdate.setLongitude(parking.getLongitude());
            parkingToUpdate.setCameraMacAddress(parking.getCameraMacAddress());
            parkingToUpdate.setCameraIpAddress(parking.getCameraIpAddress());
            parkingToUpdate.setCheckInCode(parking.getCheckInCode());
            parkingToUpdate.setCheckOutCode(parking.getCheckOutCode());

            return parkingRepo.save(parkingToUpdate);
        }
        return null;
    }


    public void deleteParking(Long id) {
        Parking parking = parkingRepo.findById(id).orElse(null);
        if (parking != null) {
            parking.setIsActive(false);
            parkingRepo.save(parking);
        }
    }
}