package com.cnu.assignment02;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {
    public String imagePath;
    public ImageOperation operation;
    private File inputFile;
    private BufferedImage inputImage;
    private BufferedImage operatedImage;

    public void start() {
        operatedImage = operation.start(inputImage);
    }

    public void sanitizeInput() throws IOException {
        inputFile = new File(imagePath);
        inputImage = ImageIO.read(inputFile);
        operation.sanitizeInput(inputImage);
    }

    public Boolean isValid() {
        return operation.isValid();
    }

    public void save(String outputPath) throws IOException {
        File outputFile = new File(outputPath, inputFile.getName());
        ImageIO.write(operatedImage, FilenameUtils.getExtension(imagePath), outputFile);
    }
}
