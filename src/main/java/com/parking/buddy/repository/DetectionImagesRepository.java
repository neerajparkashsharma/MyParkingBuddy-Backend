package com.parking.buddy.repository;

import com.parking.buddy.entity.DetectionImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface DetectionImagesRepository extends JpaRepository<DetectionImages, Long>{
    List<DetectionImages> findByParkingIdAndUserIdOrderByDetectionDateDesc(Long parkingId, Long userId);
    List<DetectionImages> findByUserId(Long userId);
}
