package com.parking.buddy.entity.DTO;

import lombok.Data;
import  java.util.List;

@Data
public class ParkingBookingRequest
{

    public Long parkingId;
    public Long customerId;
    public String bookingFromDateTime;
    public String bookingToDateTime;

    public List<String>  bookingDates;


}
