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

    public List<File> getImageFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        List<File> imageFiles = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".png")) {
                    imageFiles.add(file);
                }
            }
        }

        return imageFiles;
    }



    }
