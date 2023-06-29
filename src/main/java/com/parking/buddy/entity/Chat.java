package com.parking.buddy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Chat")
public class Chat {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "sender_id")
    private Long senderId;


    @Column(name = "booking_id")
    private Long bookingId;


    @Column(name = "sent_on")
    private Date sentOn;




}