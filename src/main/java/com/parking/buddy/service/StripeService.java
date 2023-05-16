package com.parking.buddy.service;

import com.parking.buddy.entity.request.PaymentRequest;
import com.parking.buddy.exception.InvalidRequestException;import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.naming.AuthenticationException;
//import javax.smartcardio.CardException;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String secretKey;


    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public ResponseEntity<?> addCard(String cardNumber, String expMonth, String expYear, String cvc, String email) {
        Stripe.apiKey = secretKey;
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", cardNumber);
        cardParams.put("exp_month", expMonth);
        cardParams.put("exp_year", expYear);
        cardParams.put("cvc", cvc);

        Map<String, Object> paymentMethodParams = new HashMap<>();
        paymentMethodParams.put("type", "card");
        paymentMethodParams.put("card", cardParams);

        try {
            PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);

            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("email", email);
            customerParams.put("payment_method", paymentMethod.getId());

            Customer customer = Customer.create(customerParams);

            return ResponseEntity.ok().body("Card added successfully to customer " + customer.getId());
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }



    public ResponseEntity<?> makePayment(String paymentMethodId,String customerId, long amount) {
        Stripe.apiKey = secretKey;
        try {
            Customer customer = Customer.retrieve(customerId);
            PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                    .setAmount(amount)
                    .setCurrency("usd")
                    .setPaymentMethod(paymentMethodId)
                    .setCustomer(customerId)
                    .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.AUTOMATIC)
                    .setConfirm(true)
                    .build();
            PaymentIntent paymentIntent = PaymentIntent.create(createParams);
            return ResponseEntity.ok().body("Payment successful with payment intent ID: " + paymentIntent.getId());
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    public Charge charge(PaymentRequest request) throws AuthenticationException, InvalidRequestException, CardException, StripeException {

        Map<String, Object> chargeParams = new HashMap<>();

        chargeParams.put("amount", request.getAmount());
        chargeParams.put("currency", request.getCurrency());
        chargeParams.put("description", request.getDescription());
        chargeParams.put("source", request.getSource());

        return Charge.create(chargeParams);

    }

}