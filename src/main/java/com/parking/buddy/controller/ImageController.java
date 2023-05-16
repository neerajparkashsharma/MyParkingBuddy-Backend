package com.parking.buddy.controller;


import com.parking.buddy.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;
    @GetMapping("/images")
    public List<String> getImageFiles(String directoryPath) {
        return imageService.getImagesInDirectory(directoryPath);
    }


}
