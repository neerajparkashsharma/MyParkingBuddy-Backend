package com.parking.buddy.controller;

import com.parking.buddy.entity.EWallet;
import com.parking.buddy.entity.User;
import com.parking.buddy.repository.UserRepository;
import com.parking.buddy.service.EWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import  java.util.List;
import java.util.Optional;

@RestController
public class EWalletController {
    @Autowired
    private EWalletService eWalletService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/wallet/{userId}")
    public EWallet createEWallet(@RequestBody  EWallet eWallet, @PathVariable Long userId){

       Optional<User> u= userRepository.findById(userId);

        u.ifPresent(eWallet::setUser);

        return eWalletService.createWallet(eWallet);
    }
    @GetMapping("/wallet")
    public List<EWallet> getWallet(){
        return eWalletService.getEWallets();
    }

}
