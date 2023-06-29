package com.parking.buddy.entity.request;

import lombok.Data;

@Data
public class ParkingCheckOutRequest {

        private Long parkingId;
        private Long userId;
        private String checkOutCode;
        private Long bookingId;

}
