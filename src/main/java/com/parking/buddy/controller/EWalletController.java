package com.parking.buddy.controller;

import com.parking.buddy.entity.EWallet;
import com.parking.buddy.service.EWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import  java.util.List;

@RestController
public class EWalletController {
    @Autowired
    private EWalletService eWalletService;
    @PostMapping("/wallet")
    public EWallet createEWallet(EWallet eWallet){
        return eWalletService.createWallet(eWallet);
    }
    @GetMapping("/wallet")
    public List<EWallet> getWallet(){
        return eWalletService.getEWallets();
    }

}
