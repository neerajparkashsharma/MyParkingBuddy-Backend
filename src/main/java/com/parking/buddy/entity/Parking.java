package com.parking.buddy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Parking")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private Date createDate;

    @Column(name = "updated_date")
    private Date updatedDate;
    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "parking_location")
    private String parkingLocation;

    @Column(name = "parking_charges")
    private float parkingCharges;

    @Column(name = "parking_image")
    private String parkingImage;

    @Column(name = "is_booked")
    private Boolean isBooked;

    @Column(name = "is_cancelled")
    private Boolean isCancelled;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "description")
    private String description;

    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "camera_mac_address")
    private String cameraMacAddress;

    @Column(name = "camera_ip_address")
    private String cameraIpAddress;
    @Column(name = "check_in_code")
    private String checkInCode;

    @Column(name = "check_out_code")
    private String checkOutCode;
    //endregion


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "host_id", nullable = false)
    @JsonIgnore
    private User host;

}