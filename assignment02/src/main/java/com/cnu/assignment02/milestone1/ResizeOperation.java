package com.cnu.assignment02.milestone1;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResizeOperation extends ImageOperation {
    public int height;
    public int width;

    private void fixResolution(BufferedImage inputImage) {
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

    private BufferedImage resize(BufferedImage inputImage) {
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
        return resize(inputImage);
    }
}
