package com.cnu.assignment02.milestone1;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageConverter {
    public String imagePath;
    public File inputFile;
    public List<ImageOperation> operations;
    public BufferedImage inputImage;
    public BufferedImage operatedImage;

    public void convert() throws IOException {
        inputFile = new File(imagePath);
        inputImage = ImageIO.read(inputFile);
        operatedImage = inputImage;
        for (ImageOperation operation: operations) {
            operatedImage = operation.start(operatedImage);
        }
    }

    public void save(String outputPath) throws IOException {
        File outputFile = new File(outputPath, inputFile.getName());
        ImageIO.write(operatedImage, FilenameUtils.getExtension(imagePath), outputFile);
    }
}
