package com.cnu.assignment02.milestone1;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScaleOperation extends ImageOperation {
    public int height;
    public int width;

    private void fixResolution(BufferedImage inputImage) {
        if (width == 0 && height == 0) {
            width = inputImage.getWidth();
            height = inputImage.getHeight();
        }
        else if (width == 0) {
            width = (int) Math.floor((double)inputImage.getWidth() / (double)inputImage.getHeight() * (double)height);
        }
        else if (height == 0) {
            height = (int) Math.floor((double)inputImage.getHeight() / (double)inputImage.getWidth() * (double)width);
        }
    }

    private BufferedImage scale(BufferedImage inputImage) {
        int type = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType();
        fixResolution(inputImage);
        BufferedImage operatedImage = new BufferedImage(width, height, type);
        Graphics2D g = operatedImage.createGraphics();
        g.drawImage(inputImage, 0, 0, width, height, null);
        g.dispose();
        return operatedImage;
    }

    @Override
    public BufferedImage start(BufferedImage inputImage) {
        width = width*inputImage.getWidth();
        height = height*inputImage.getHeight();
        return scale(inputImage);
    }
}
