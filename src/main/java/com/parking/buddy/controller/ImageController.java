package com.parking.buddy.controller;


import com.parking.buddy.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Path;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/images")
    public List<String> getImageFiles(@RequestParam("directoryPath") String directoryPath) {
        String decodedPath = URLDecoder.decode(directoryPath, StandardCharsets.UTF_8);
        List<String> imageNames = imageService.getImagesInDirectory(decodedPath);


        System.out.println("imageNames: " + imageNames);

        return imageNames;

    }

    @GetMapping(value = "/images/files/", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getImagesFiles(@RequestParam("directoryPath") String directoryPath) {
        try {
            String decodedPath = URLDecoder.decode(directoryPath, StandardCharsets.UTF_8);
            List<File> imageFiles = imageService.getImageFilesInDirectory(decodedPath);

            System.out.println("....inside....");
            List<ByteArrayResource> resources = imageFiles.stream()
                    .map(file -> {
                        try {
                            byte[] fileContent = Files.readAllBytes(file.toPath());
                            return new ByteArrayResource(fileContent);
                        } catch (IOException e) {
                            // Handle file read error
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            for (int i = 0; i < resources.size(); i++) {
                body.add("files", resources.get(i));
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(body);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}