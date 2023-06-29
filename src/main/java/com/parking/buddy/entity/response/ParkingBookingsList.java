package com.parking.buddy.entity.response;

import com.parking.buddy.entity.BookingDates;
import com.parking.buddy.entity.ParkingBookingRecords;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParkingBookingsList {


    public ParkingBookingRecords parkingBookingRecords;
    public List<BookingDates> bookingDates;


}
