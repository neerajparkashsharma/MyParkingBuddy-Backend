package com.parking.buddy.entity;


//import lombok.Data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Parking_Records")
@Data

public class ParkingRecords
{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long Id;
    private long customerId;

    private Date checkIn;

    private Date checkOut;

    private String parkingLocation;

    private String parkingCharges;

    private String parkingImage;

    private Boolean isActive;

    private Date createdDate;

    private Date updatedDate;

    private Long createdBy;

    private Long updatedBy;






}
