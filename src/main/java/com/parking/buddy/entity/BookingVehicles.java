package com.parking.buddy.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "Booking_Vehicles")
@Entity
public class BookingVehicles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="booking_id")
    private ParkingBookingRecords parkingBookingRecords;


    @Column(name="customer_id")
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;


    @Column(name="is_allowed")
    private Boolean isAllowed;

    @Column(name="check_in")
    private Date checkIn;

    @Column(name="check_out")
    private Date checkOut;


    @Column(name="check_in_input")
    private String checkInInput;

    @Column(name="check_out_input")
    private String checkOutInput;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingBookingRecords getParkingBookingRecords() {
        return parkingBookingRecords;
    }

    public void setParkingBookingRecords(ParkingBookingRecords parkingBookingRecords) {
        this.parkingBookingRecords = parkingBookingRecords;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Boolean getAllowed() {
        return isAllowed;
    }

    public void setAllowed(Boolean allowed) {
        isAllowed = allowed;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }


    public String getCheckInInput() {
        return checkInInput;
    }

    public void setCheckInInput(String checkInInput) {
        this.checkInInput = checkInInput;
    }

    public String getCheckOutInput() {
        return checkOutInput;
    }

    public void setCheckOutInput(String checkOutInput) {
        this.checkOutInput = checkOutInput;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BookingVehicles() {
    }

    public BookingVehicles(Long id, ParkingBookingRecords parkingBookingRecords, Long customerId, Vehicle vehicle, Boolean isAllowed, Date checkIn, Date checkOut, String checkInInput, String checkOutInput) {
        this.id = id;
        this.parkingBookingRecords = parkingBookingRecords;
        this.customerId = customerId;
        this.vehicle = vehicle;
        this.isAllowed = isAllowed;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.checkInInput = checkInInput;
        this.checkOutInput = checkOutInput;
    }
}
