package com.parking.buddy.controller;


import com.parking.buddy.entity.DetectionImages;
import com.parking.buddy.repository.DetectionImagesRepository;
import com.parking.buddy.service.DetectionImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
public class DetectionImageController {

    @Autowired
    private DetectionImagesService detectionImagesService;

    @Autowired
    private DetectionImagesRepository imageRepository;



    @PostMapping("/save-image")
    public String saveImage(@RequestBody DetectionImages request) {
        String imageData = request.getImage();
        try {
            // Replace URL-safe characters
            String base64String = imageData.replace('-', '+').replace('_', '/');

            // Decode the Base64 image data
            byte[] imageBytes = Base64.getDecoder().decode(base64String);

            // Compress the image
            byte[] compressedImageBytes = compressImage(imageBytes);

            // Encode the compressed image back into Base64 format
            String compressedImage = Base64.getEncoder().encodeToString(compressedImageBytes);
            request.setImage(compressedImage);

            // Save the compressed image
            imageRepository.save(request);

            return "Image saved successfully.";
        } catch (Exception e) {
            return "Failed to save the image.";
        }
    }

    @GetMapping("/all")
    public List<String> getAllImages() {
        List<DetectionImages> images = imageRepository.findAll();
        List<String> base64Images = new ArrayList<>();

        for (DetectionImages image : images) {
            base64Images.add(image.getImage());
        }

        return base64Images;
    }



    public byte[] compressImage(byte[] imageData) throws IOException {
        // Create a BufferedImage from the image data
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        BufferedImage image = ImageIO.read(inputStream);

        // Create a ByteArrayOutputStream to store the compressed image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Compress the image using the desired format (e.g., JPEG or PNG) and quality
        ImageIO.write(image, "JPEG", outputStream); // Adjust the format and compression quality as per your requirements

        // Get the compressed image data as a byte array
        byte[] compressedData = outputStream.toByteArray();

        // Close the streams
        outputStream.close();
        inputStream.close();

        return compressedData;
    }
}
