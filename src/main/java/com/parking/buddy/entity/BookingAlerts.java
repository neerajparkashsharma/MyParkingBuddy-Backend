package com.parking.buddy.entity;


import javax.persistence.*;
import java.util.Date;

@Entity

public class BookingAlerts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@Column(name="message")
    private String message;

@Column(name="image")
    private String image;

@Column(name="created_date")
    private Date createdDate;

@Column(name="updated_date")
    private Date updatedDate;

@Column(name="created_by")
    private Long createdBy;

@Column(name="updated_by")
    private Long updatedBy;

@Column(name="is_active")
    private Boolean isActive;


    @ManyToOne
    @JoinColumn(name="booking_id")
    private ParkingBookingRecords parkingBookingRecords;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public ParkingBookingRecords getParkingBookingRecords() {
        return parkingBookingRecords;
    }

    public void setParkingBookingRecords(ParkingBookingRecords parkingBookingRecords) {
        this.parkingBookingRecords = parkingBookingRecords;
    }

    public BookingAlerts() {
    }



    public BookingAlerts(String message, String image, Date createdDate, Date updatedDate, Long createdBy, Long updatedBy, Boolean isActive, ParkingBookingRecords parkingBookingRecords) {
        this.message = message;
        this.image = image;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isActive = isActive;
        this.parkingBookingRecords = parkingBookingRecords;
    }


}
