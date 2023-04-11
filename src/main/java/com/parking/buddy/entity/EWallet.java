package com.parking.buddy.entity;

import javax.persistence.*;
import java.util.Date;

import java.util.List;
@Entity
@Table(name = "E_Wallet")
public class EWallet {

    @Id
    private Long id;


    @Column(name = "amount")
    private Float amount;


    @Column(name = "type")
    private  String type;



    @ManyToOne
    @JoinColumn(name="user_id")

    private  User user;





    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")

    private Long  createdBy;

    @Column(name = "created_on")

    private Date createdOn;



    @Column(name = "updated_by")
    private Long updatedBy;



    @Column(name = "updated_on")
    private  Date updatedOn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public EWallet(){

    }

    public EWallet(Long id, Float amount, String type, User user, Boolean isActive, Long createdBy, Date createdOn, Long updatedBy, Date updatedOn) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.user = user;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
    }
}
