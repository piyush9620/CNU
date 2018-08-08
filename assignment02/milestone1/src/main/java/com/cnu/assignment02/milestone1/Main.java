package com.cnu.assignment02.milestone1;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private final static String INPUT_LIST_PATH = "/var/data/input/jobs.json";
//    private final static String INPUT_LIST_PATH = "/tmp/test.json";
    private final static String OUTPUT_IMAGE_PATH = "/var/data/output/";
//    private final static String OUTPUT_IMAGE_PATH = "/tmp";

    public static List<ImageOperation> parseInput(String operationsPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ImageOperation> imageOperations = objectMapper.readValue(
                new File(operationsPath),
                new TypeReference<List<ImageOperation>>(){}
                );

        return imageOperations;
    }

    public static void main(String args[]) throws IOException {
        List<ImageOperation> imageOperations = parseInput(INPUT_LIST_PATH);
        imageOperations.stream().forEach((imageOperation) -> {
            try {
                imageOperation.start();
                imageOperation.save(OUTPUT_IMAGE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Done");
    }


}