package com.parking.buddy.service;

import com.parking.buddy.entity.EWallet;
import com.parking.buddy.entity.User;
import com.parking.buddy.exception.ResourceNotFoundException;
import com.parking.buddy.repository.EWalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EWalletService {
    @Autowired
    private EWalletRepository eWalletRepository;

    public EWallet createWallet(EWallet eWallet) {
        return eWalletRepository.save(eWallet);
    }

    public List<EWallet> getEWallets() {
        return eWalletRepository.findAll();
    }

    public EWallet getEWalletById(Long id) {
        return eWalletRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Wallet", "id", id));
    }

    public EWallet getWalletByUser(User u) {
        EWallet eWallet = eWalletRepository.findByUser(u);
        if (eWallet == null) {
            throw new ResourceNotFoundException("Wallet", "user", u);
        }
        return eWallet;
    }

}
