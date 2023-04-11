package com.parking.buddy.repository;

import com.parking.buddy.entity.EWallet;
import com.parking.buddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EWalletRepository extends JpaRepository<EWallet,Long> {


    EWallet findByUser(User u);
}
