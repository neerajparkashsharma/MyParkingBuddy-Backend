package com.parking.buddy.controller;

import com.parking.buddy.entity.CardDetails;
import com.parking.buddy.entity.request.PaymentRequest;
import com.parking.buddy.service.CardDetailsService;
import com.parking.buddy.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stripe/payment")
public class StripePaymentController {

    @Autowired
    private StripeService stripeService;

    @Autowired
    private CardDetailsService cardDetailsService;

    @PostMapping("/add")
    public ResponseEntity<?> addCard(
            @RequestParam("cardNumber") String cardNumber,
            @RequestParam("expMonth") String expMonth,
            @RequestParam("expYear") String expYear,
            @RequestParam("cvc") String cvc,
            @RequestParam("email") String email) {
        return stripeService.addCard(cardNumber, expMonth, expYear, cvc, email);
    }

    @PostMapping("/charge")
    public ResponseEntity<?> chargeCard(@RequestBody PaymentRequest request) {
        try {
            stripeService.charge(request);
            return ResponseEntity.ok().body("Payment Successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PostMapping("/make-payment")
    public ResponseEntity<?> makePayment(
            @RequestParam String paymentMethodId,
            @RequestParam int amount,
            @RequestParam String customerId) {

        return stripeService.makePayment(paymentMethodId, customerId, amount);
    }

    @PostMapping("/CardDetails/")
    public CardDetails createCardDetails(@RequestBody  CardDetails cardDetails ){
        return  cardDetailsService.createCard(cardDetails);
    }
}