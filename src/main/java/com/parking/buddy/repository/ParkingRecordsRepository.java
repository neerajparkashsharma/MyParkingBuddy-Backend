package com.parking.buddy.repository;

import com.parking.buddy.entity.ParkingRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRecordsRepository extends JpaRepository<ParkingRecords, Long> {

}
