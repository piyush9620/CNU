package com.cnu.assignment02;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private final static String INPUT_LIST_PATH = System.getenv("INPUT_LIST_PATH");
    private final static String OUTPUT_IMAGE_PATH = System.getenv("OUTPUT_IMAGE_PATH");

    private static List<ImageConverter> parseInput(String conversionsPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ImageConverter> imageConversions = objectMapper.readValue(
                new File(conversionsPath),
                new TypeReference<List<ImageConverter>>(){}
        );

        return imageConversions;
    }


    public static void main(String args[]) throws IOException {
        List<ImageConverter> imageConversions = parseInput(INPUT_LIST_PATH);
        imageConversions
                .parallelStream()
                .forEach(imageConversion -> {
                    try {
                        imageConversion.sanitizeOperations();
                        imageConversion.start();
                        imageConversion.save(OUTPUT_IMAGE_PATH);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println("Done");
    }

}