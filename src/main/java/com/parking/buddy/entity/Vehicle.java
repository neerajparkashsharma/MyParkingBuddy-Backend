package com.parking.buddy.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "Vehicle")

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_image")
    private String vehicleImage;

    @Column(name = "number_plate_image")
    private String numberPlateImage;

    @Column(name = "number_plate")
    private String numberPlate;

    @OneToMany(mappedBy = "vehicle")
    private List<BookingVehicles> bookingVehicles;


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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleImage() {
        return vehicleImage;
    }

    public void setVehicleImage(String vehicleImage) {
        this.vehicleImage = vehicleImage;
    }

    public String getNumberPlateImage() {
        return numberPlateImage;
    }

    public void setNumberPlateImage(String numberPlateImage) {
        this.numberPlateImage = numberPlateImage;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public List<BookingVehicles> getBookingVehicles() {
        return bookingVehicles;
    }

    public void setBookingVehicles(List<BookingVehicles> bookingVehicles) {
        this.bookingVehicles = bookingVehicles;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Vehicle() {
    }

    public Vehicle(Long id, String vehicleImage, String numberPlateImage, String numberPlate, List<BookingVehicles> bookingVehicles, Date createdDate, Date updatedDate, Long createdBy, Long updatedBy, Boolean isActive) {
        this.id = id;
        this.vehicleImage = vehicleImage;
        this.numberPlateImage = numberPlateImage;
        this.numberPlate = numberPlate;
        this.bookingVehicles = bookingVehicles;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isActive = isActive;
    }
}
