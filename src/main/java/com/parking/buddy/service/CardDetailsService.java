package com.parking.buddy.service;

import com.parking.buddy.entity.CardDetails;
import com.parking.buddy.exception.ResourceAlreadyExistsException;
import com.parking.buddy.repository.CardDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardDetailsService {


    @Autowired
    private CardDetailsRepository cardDetailsRepository;

    public List<CardDetails> getAllCards() {
        return cardDetailsRepository.findAll();
    }

    public CardDetails getCardById(Long id) {
        return cardDetailsRepository.findById(id).orElse(null);
    }

    public CardDetails createCard(CardDetails cardDetails) throws ResourceAlreadyExistsException {
        if (cardDetails.getId() != null && cardDetailsRepository.existsById(cardDetails.getId())) {
            throw new ResourceAlreadyExistsException("Card", "id", cardDetails.getId());
        }
        try {
            return cardDetailsRepository.save(cardDetails);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("Card", "card number", cardDetails.getNumber());
        }
    }



}
