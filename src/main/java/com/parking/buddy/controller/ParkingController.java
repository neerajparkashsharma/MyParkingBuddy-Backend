package com.parking.buddy.controller;

import com.parking.buddy.entity.DTO.ParkingDTO;
import com.parking.buddy.entity.Parking;
import com.parking.buddy.entity.User;
import com.parking.buddy.service.ParkingService;
import com.parking.buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController

@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ParkingController {


    @Autowired
    public ParkingService parkingService;

    @Autowired
    public UserService userService;


    @GetMapping("/parking")
    public List<Parking> FindAllParking() {
        return parkingService.getAllParking();
    }


    @DeleteMapping("/parking/{id}")
    public void deleteParking(@PathVariable("id") Long id) {
        parkingService.deleteParking(id);
    }

    @GetMapping("/parking/host/{id}")
    public List<Parking> getParkingByHostId(@PathVariable("id") Long id) {
        return parkingService.getParkingByHostId(id);
    }

    @PutMapping("/updateParking")
    public Parking updateParking(@RequestBody Parking parking) {
        return parkingService.updateParking(parking);
    }


    @GetMapping("/parking/{latitude}/{longitude}")
    public List<Parking> FindAllParking(@PathVariable("latitude") double latitude,
                                        @PathVariable("longitude") double longitude,
                                        @RequestParam("userId") double userId
    ) {
        User u = userService.getUserById((long) userId);
        if (u.getLocationDistance() == null)
            u.setLocationDistance(5.0);


        List<Parking> allParking = parkingService.getAllParking();

        List<Parking> filteredParking = new ArrayList<>();
        for (Parking parking : allParking) {
            double parkingLatitude = Double.parseDouble(parking.getLatitude()==null? String.valueOf(0) :parking.getLatitude());
            double parkingLongitude = Double.parseDouble(parking.getLongitude()==null? String.valueOf(0) :parking.getLongitude());
            double distanceInKm = distance(latitude, longitude, parkingLatitude, parkingLongitude);

                if (distanceInKm <= u.getLocationDistance()) {
                    filteredParking.add(parking);
                }

        }
        return filteredParking;
    }


    // Helper method to calculate distance between two points
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344; // Convert to km
        return (dist);
    }


    @PostMapping("/parking")
    public String saveParking(@RequestBody ParkingDTO parking) {
        return parkingService.saveParking(parking);
    }

    @GetMapping("/parking/{id}")
    public Parking findParkingById(@PathVariable Long id) {
        return parkingService.getParkingById(id);
    }

    @GetMapping("/parking/camera/{macAddress}")
    public Parking findParkingByCamera(@PathVariable String macAddress) {

        return parkingService.getParkingByCameraMacAddress(macAddress);
    }





//    @PostMapping("/detect-image")
//    public ResponseEntity<?> detect(@RequestBody ImageRequest imageRequest) throws IOException {
//
//        // Decode the image data from base64
//        byte[] imageBytes = Base64.getDecoder().decode(imageRequest.getImage());
//
//        // Encode the resulting image in JPEG format and return it as a byte array
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        byte[] responseBytes = baos.toByteArray();
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}