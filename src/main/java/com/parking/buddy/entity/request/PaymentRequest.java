package com.parking.buddy.entity.request;

import lombok.Data;

@Data
public class PaymentRequest {

    public String source;
    public String amount;
    public String currency;
    public String description;

//    {
//        "description": "Example charge",
//            "source": "tok_visa",
//            "currency": "EUR",
//            "amount": "1000"
//    }


}
