package com.parking.buddy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Table(name = "Parking_Booking_Records")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingBookingRecords {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "parking_id")
//    @JsonIgnore
    private Parking parking;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private User customer;


    @OneToMany(mappedBy = "parkingBookingRecords")
    @JsonIgnore
    private List<BookingVehicles> bookingVehicles;


    @Column(name = "park_from_date")
    private Date parkFromDate;

    @Column(name = "park_to_date")
    private Date parkToDate;

    @Column(name = "is_expired")
    private Boolean isExpired;


    @Column(name = "created_date")
    private Date createdDate;


    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "is_active")
    private Boolean isActive;


    @Column(name= "total_parking_charges")
    private Float totalParkingCharges;


}
