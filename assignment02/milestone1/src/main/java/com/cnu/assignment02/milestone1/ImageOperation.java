package com.cnu.assignment02.milestone1;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageOperation {
    public String imagePath;
    public int height;
    public int width;
    private File inputFile;
    private BufferedImage inputImage;
    private BufferedImage operatedImage;

    public ImageOperation() {
        inputFile = new File(imagePath);
    }

    private void fixResolution() {
        if (width == 0 && height == 0) {
            width = inputImage.getWidth();
            height = inputImage.getHeight();
        }
        else if (width == 0) {
            width = (int) Math.round((double)width / (double)height * (double)height);
        }
        else if (height == 0) {
            height = (int) Math.round((double)height / (double)width * (double)width);
        }
    }

    private void resize() {
        int type = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType();
        fixResolution();
        operatedImage = new BufferedImage(width, height, type);
        Graphics2D g = operatedImage.createGraphics();
        g.drawImage(inputImage, 0, 0, width, height, null);
        g.dispose();
    }

    public void start() throws IOException {
        inputImage = ImageIO.read(inputFile);
        resize();
    }

    public void save(String outputPath) throws IOException {
        File outputFile = new File(outputPath, inputFile.getName());
        ImageIO.write(operatedImage, FilenameUtils.getExtension(imagePath), outputFile);
    }
}