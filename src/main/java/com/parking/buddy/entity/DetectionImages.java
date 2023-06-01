package com.parking.buddy.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Detection_Images")
public class DetectionImages {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "image")
    private String image;

    @Column(name = "detection_date")
    private LocalDateTime detectionDate;

    @Column(name = "additional_text")
    private String additionalText;

    @Column(name = "detection_type")
    private String detectionType;

    @Column(name = "user_id")
    private Long userId;

}
