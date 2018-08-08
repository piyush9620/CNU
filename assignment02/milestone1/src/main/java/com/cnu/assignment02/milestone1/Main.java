package com.cnu.assignment02.milestone1;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public class Main {

    private final static String INPUT_LIST_PATH = "/var/data/input/jobs.json";
    private final static String OUTPUT_IMAGE_PATH = "/var/data/output/";

    public static List<InputImage> parseInput(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<InputImage> inputImages = objectMapper.readValue(new File(path), new TypeReference<List<InputImage>>(){});

        return inputImages;
    }

    public static void resizeImageWithPath(String inputImagePath,
                                           String outputImagePath,
                                           ImageResolution requiredResolution) throws IOException {
        File originalImageFile = new File(inputImagePath);
        BufferedImage originalImage = ImageIO.read(originalImageFile);

        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizeImageJpg = ImageConverter.resizeImage(originalImage, requiredResolution, type);
        ImageIO.write(resizeImageJpg, FilenameUtils.getExtension(inputImagePath),
                new File(outputImagePath, originalImageFile.getName()));
    }

    public static void main(String args[]) throws IOException {
        List<InputImage> inputImages = parseInput(INPUT_LIST_PATH);
        inputImages.stream().forEach((inputImage) -> {
            try {
                resizeImageWithPath(inputImage.imagePath, OUTPUT_IMAGE_PATH, new ImageResolution(inputImage.width, inputImage.height));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Done");
    }


}