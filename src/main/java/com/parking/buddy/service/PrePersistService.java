package com.parking.buddy.service;


import com.parking.buddy.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PrePersistService {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void SaveRoleIfNotExist() {
        System.out.println("PrePersistService init");

        // Create roles if not exists

        if(roleRepository.findByName("Host") == null)
            roleRepository.save(new com.parking.buddy.entity.Role(1L,"Host"));
        if(roleRepository.findByName("Customer") == null)
            roleRepository.save(new com.parking.buddy.entity.Role(2L,"Customer"));


    }

}
