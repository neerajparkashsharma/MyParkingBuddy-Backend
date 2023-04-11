package com.parking.buddy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "Parking_Booking_Records")
@Entity
public class ParkingBookingRecords {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "parking_id")

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

    public ParkingBookingRecords() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Date getParkFromDate() {
        return parkFromDate;
    }

    public void setParkFromDate(Date parkFromDate) {
        this.parkFromDate = parkFromDate;
    }

    public Date getParkToDate() {
        return parkToDate;
    }

    public void setParkToDate(Date parkToDate) {
        this.parkToDate = parkToDate;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Date getCreatedDate() {
        return createdDate;
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

    public ParkingBookingRecords(long id, Parking parking, User customer, Date parkFromDate, Date parkToDate, Boolean isExpired, Date createdDate, Date updatedDate, Long createdBy, Long updatedBy, Boolean isActive) {
        Id = id;
        this.parking = parking;
        this.customer = customer;
        this.parkFromDate = parkFromDate;
        this.parkToDate = parkToDate;
        this.isExpired = isExpired;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isActive = isActive;
    }
}
