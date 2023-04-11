package com.parking.buddy.controller;

import com.parking.buddy.entity.Vehicle;
import com.parking.buddy.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import  java.util.List;
@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    private List<Vehicle> getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/vehicles/{id}")
    private Vehicle getVehicleById(Long id){
        return vehicleService.getVehicleById(id);
    }

    @PostMapping("/vehicles")
    private Vehicle saveVehicle(@RequestBody Vehicle vehicle){
        return vehicleService.saveVehicle(vehicle);
    }


}
