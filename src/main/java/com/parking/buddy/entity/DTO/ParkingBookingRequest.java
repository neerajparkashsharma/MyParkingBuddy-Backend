package com.parking.buddy.entity.DTO;

import lombok.Data;

@Data
public class ParkingBookingRequest
{

    public Long parkingId;
    public Long customerId;
    public String bookingFromDateTime;
    public String bookingToDateTime;




//    public String bookingTime;
//    public String bookingStartTime;
//    public String bookingDurationInHours;


}
