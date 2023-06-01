package com.parking.buddy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "MM")
    private  String MM;

    @Column(name = "YY")
    private  String YY;

    @Column(name = "CVV")
    private  String CVV;


    @Column(name = "card_number")
    private  String cardNumber;

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

    @Column(name =  "customer_id")
    private String customerId;





}
