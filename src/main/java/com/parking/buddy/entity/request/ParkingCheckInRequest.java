package com.parking.buddy.entity.request;

import lombok.Data;

@Data
public class ParkingCheckInRequest {
   private Long parkingId;
   private Long userId;
   private String checkInCode;
   private Long bookingId;

}
