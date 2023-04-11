package com.parking.buddy.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CardDetails")
public class CardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private  String number;


    @Column(name = "expiry_date")
    private Date expiryDate;


    @Column(name = "account_title")
    private String accountTitle;

    @Column(name = "amount")
private Float amount;

    @Column(name = "cvv")
    private String cvv;


    @Column(name = "created_on")
    private Date createDate;

    @Column(name = "updated_on")
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getAccountTitle() {
        return accountTitle;
    }

    public void setAccountTitle(String accountTitle) {
        this.accountTitle = accountTitle;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public CardDetails() {
    }

    public CardDetails(Long id, String number, Date expiryDate, String accountTitle, String cvv, Date createDate, Date updatedDate, Long createdBy, Long updatedBy, Boolean isActive,Float amount) {
        this.id = id;
        this.number = number;
        this.expiryDate = expiryDate;
        this.accountTitle = accountTitle;
        this.cvv = cvv;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isActive = isActive;
        this.amount  = amount;
    }
}
