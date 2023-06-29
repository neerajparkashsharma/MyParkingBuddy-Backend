package com.parking.buddy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Booking_Dates")
public class BookingDates {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "booking_date")
    private Date bookingDate;


    @Column(name = "parking_booking_id")
    private Long parkingBookingId;


}
