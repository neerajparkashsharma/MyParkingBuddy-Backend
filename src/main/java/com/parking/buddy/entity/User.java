package com.parking.buddy.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity

@Table(name = "Users",
        uniqueConstraints={@UniqueConstraint(columnNames ={"email_address","phone_number"})})
public class User {


    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;


    @Column(name="name")
    private String name;

    @Column(name="email_address",unique = true)
    private String emailAddress;


    @Column(name="phone_number",unique = true)

    private String phoneNumber;

    @Column(name="password")
    private String password;


    @Column(name="is_active")
    private boolean isActive;




    @OneToMany(mappedBy="user")

    private List<EWallet> eWallets;


    @OneToMany(mappedBy="host")

    private List<Parking> parking;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public List<Parking> getParking() {
        return parking;
    }

    public void setParking(List<Parking> parking) {
        this.parking = parking;
    }

    @Column(name="created_on")
    private Date createdDate;


    @Column(name="updated_on")
    private Date updatedDate;

    @Column(name="created_by")
    private Long createdBy;

    @Column(name="updated_by")
    private Long updatedBy;


    @Column(name = "location_distance")
    private Double locationDistance;

    @OneToMany(mappedBy="customer")


    private List<ParkingBookingRecords> bookingRecords;

    @ManyToOne
    @JoinColumn(name="role_id")

    private Role role;

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }


    public String getName() {
        return name;
    }


    public List<ParkingBookingRecords> getBookingRecords() {
        return bookingRecords;
    }

    public void setBookingRecords(List<ParkingBookingRecords> bookingRecords) {
        this.bookingRecords = bookingRecords;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public List<EWallet> geteWallets() {
        return eWallets;
    }

    public void seteWallets(List<EWallet> eWallets) {
        this.eWallets = eWallets;
    }

    public Double getLocationDistance() {
        return locationDistance;
    }

    public void setLocationDistance(Double locationDistance) {
        this.locationDistance = locationDistance;
    }

    public User(long id, String name, String emailAddress, String phoneNumber, String password, boolean isActive, List<EWallet> eWallets, List<Parking> parking, Date createdDate, Date updatedDate, Long createdBy, Long updatedBy, List<ParkingBookingRecords> bookingRecords, Role role) {
        Id = id;
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isActive = isActive;
        this.eWallets = eWallets;
        this.parking = parking;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.bookingRecords = bookingRecords;
        this.role = role;
    }

    public User() {
    }
}
