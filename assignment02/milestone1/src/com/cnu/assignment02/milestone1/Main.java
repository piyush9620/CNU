package com.cnu.assignment02.milestone1;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private final String inputList = "/tmp/tmp.json";

    public static List<InputImage> parseInput(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<InputImage> inputImages;
        inputImages = objectMapper.readValue(new File(path), new TypeReference<List<InputImage>>() {});

        return inputImages;
    }

    public static void main(String args[]) {


    }


}