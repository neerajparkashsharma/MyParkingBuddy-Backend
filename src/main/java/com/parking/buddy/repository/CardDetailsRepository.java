package com.parking.buddy.repository;

import com.parking.buddy.entity.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CardDetailsRepository extends JpaRepository<CardDetails, Long> {

    List<CardDetails> findByUserId(Long userId);

}
