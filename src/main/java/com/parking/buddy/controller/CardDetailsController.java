package com.parking.buddy.controller;

import com.parking.buddy.entity.CardDetails;
import com.parking.buddy.service.CardDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class CardDetailsController {
    @Autowired
    private CardDetailsService cardDetailsService;
    @GetMapping("/CardDetails/{id}")
    public CardDetails getCardDetailsById(Long id)
    {
        return  cardDetailsService.getCardById(id);
    }
    @GetMapping("/CardDetails/")
    public  List<CardDetails> getAll()
    {
        return  cardDetailsService.getAllCards();
    }
    @PostMapping("/CardDetails/")
    public  CardDetails createCardDetails(@RequestBody  CardDetails cardDetails ){
        return  cardDetailsService.createCard(cardDetails);
    }
}
