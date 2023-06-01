package com.parking.buddy.repository;

import com.parking.buddy.entity.DetectionImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectionImagesRepository extends JpaRepository<DetectionImages, Long>{
}
