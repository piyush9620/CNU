package com.cnu.assignment02.milestone1;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScaleOperation extends ImageOperation {
    public double height;
    public double width;
    private int widthInt;
    private int heightInt;

    private void fixResolution(BufferedImage inputImage) {
        if (widthInt == 0 && heightInt == 0) {
            widthInt = inputImage.getWidth();
            heightInt = inputImage.getHeight();
        }
        else if (widthInt == 0) {
            widthInt = (int) Math.floor((double)inputImage.getWidth() / (double)inputImage.getHeight() * (double)heightInt);
        }
        else if (heightInt == 0) {
            heightInt = (int) Math.floor((double)inputImage.getHeight() / (double)inputImage.getWidth() * (double)widthInt);
        }
    }

    private BufferedImage scale(BufferedImage inputImage) {
        int type = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType();
        fixResolution(inputImage);
        BufferedImage operatedImage = new BufferedImage(widthInt, heightInt, type);
        Graphics2D g = operatedImage.createGraphics();
        g.drawImage(inputImage, 0, 0, widthInt, heightInt, null);
        g.dispose();
        return operatedImage;
    }

    @Override
    public BufferedImage start(BufferedImage inputImage) {
        widthInt = (int) (width * inputImage.getWidth());
        heightInt = (int) (height * inputImage.getHeight());
        return scale(inputImage);
    }
}
