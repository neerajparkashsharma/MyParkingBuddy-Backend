package com.parking.buddy.service;

import com.parking.buddy.entity.Vehicle;
import com.parking.buddy.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        try {
            return vehicleRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public Vehicle getVehicleById(Long id) {
        try {
            return vehicleRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw e;
        }
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        try {
            return vehicleRepository.save(vehicle);
        } catch (Exception e) {
            throw e;
        }
    }

    public String deleteVehicle(Long id) {
        try {
            vehicleRepository.deleteById(id);
            return "Vehicle removed !! " + id;
        } catch (Exception e) {
            throw e;
        }
    }


}
