package com.parking.buddy.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
@Service
public class ImageService {


    public List<String> getImagesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
            }
        });
        List<String> imageFiles = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                imageFiles.add(file.getName());
            }
        }
        return imageFiles;
    }



}
