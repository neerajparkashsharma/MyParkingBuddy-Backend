package com.parking.buddy.controller;


import com.parking.buddy.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Path;
import java.util.List;
@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;
    @GetMapping("/images/{directoryPath}")
    public List<String> getImageFiles(@PathVariable String directoryPath) {
        return imageService.getImagesInDirectory(directoryPath);
    }


}
