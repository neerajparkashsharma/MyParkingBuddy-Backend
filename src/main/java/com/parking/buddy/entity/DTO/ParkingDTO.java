package com.parking.buddy.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingDTO
{
    public Long id;
    public String address;
    public Long hostId;
    public  Double latitude;
    public  Double longitude;
    public String parkingImage;
    public String description;
    public String parkingLocation;
    public Float parkingCharges;
    public Boolean isBooked;
    public Boolean isCancelled;
    public Boolean isAvailable;
    public Boolean isActive;
    public Date createDate;
    public Date updatedDate;

    public String cameraMACAddress;
    public String cameraIP;

    public String CheckInCode;
    public String CheckOutCode;


}
