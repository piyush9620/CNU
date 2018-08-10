package com.cnu.assignment02;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ImageConverter {
    public String imagePath;
    public List<ImageOperation> operations;
    private File inputFile;
    private BufferedImage inputImage;
    private BufferedImage operatedImage;

    public void start() {
        operations = operations.stream()
                  .filter(imageOperation ->  imageOperation.isValid())
                  .distinct()
                  .collect(Collectors.toList());

        operatedImage = inputImage;
        for (ImageOperation operation: operations) {
            operatedImage = operation.start(operatedImage);
        }
    }

    public void sanitizeOperations() throws IOException {
        inputFile = new File(imagePath);
        inputImage = ImageIO.read(inputFile);
        operations.forEach(operation -> operation.sanitizeInput(inputImage));
    }

    public void save(String outputPath) throws IOException {
        File outputFile = new File(outputPath, inputFile.getName());
        ImageIO.write(operatedImage, FilenameUtils.getExtension(imagePath), outputFile);
    }
}
