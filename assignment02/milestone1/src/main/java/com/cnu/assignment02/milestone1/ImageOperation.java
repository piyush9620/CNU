package com.cnu.assignment02.milestone1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageOperation {
    private String imagePath;
    private Integer height;
    private Integer width;
    private File inputFile;
    private BufferedImage inputImage;
    private BufferedImage operatedImage;
    private Boolean isInvalid = false;

    @JsonCreator
    public ImageOperation(@JsonProperty("imagePath") String imagePath, @JsonProperty("height") Integer height, @JsonProperty("width") Integer width) {
        this.imagePath = imagePath;
        this.width = width;
        this.height = height;
    }

    public Boolean isValid() {
        return !isInvalid;
    }

    public void sanitizeInput() throws IOException {
        inputFile = new File(imagePath);
        inputImage = ImageIO.read(inputFile);

        if (width == null && height == null) {
            width = inputImage.getWidth();
            height = inputImage.getHeight();
        }
        else if (width == null) {
            width = (int) Math.floor((double)inputImage.getWidth() / (double)inputImage.getHeight() * (double)height);
        }
        else if (height == null) {
            height = (int) Math.floor((double)inputImage.getHeight() / (double)inputImage.getWidth() * (double)width);
        }

        if (height == 0 || width == 0) {
            isInvalid = true;
        }
    }

    private BufferedImage resize(BufferedImage inputImage) {
        int type = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType();
        operatedImage = new BufferedImage(width, height, type);
        Graphics2D g = operatedImage.createGraphics();
        g.drawImage(inputImage, 0, 0, width, height, null);
        g.dispose();
        return operatedImage;
    }

    public void start(){
        operatedImage = resize(inputImage);
    }

    public void save(String outputPath) throws IOException {
        File outputFile = new File(outputPath, inputFile.getName());
        ImageIO.write(operatedImage, FilenameUtils.getExtension(imagePath), outputFile);
    }
}