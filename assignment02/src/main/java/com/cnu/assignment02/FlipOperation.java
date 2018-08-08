package com.cnu.assignment02;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FlipOperation extends ImageOperation {
    public String orientation;

    public BufferedImage flipHorizontal(BufferedImage inputImage) {
        int type = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType();
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage operatedImage = new BufferedImage(width, height, type);
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++) {
                operatedImage.setRGB(x, y, inputImage.getRGB(width-x-1,y));
            }
        }

        return operatedImage;
    }

    public BufferedImage flipVertical(BufferedImage inputImage) {
        int type = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType();
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage operatedImage = new BufferedImage(width, height, type);
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++) {
                operatedImage.setRGB(x, y, inputImage.getRGB(x,height-y-1));
            }
        }

        return operatedImage;
    }


    @Override
    public BufferedImage start(BufferedImage inputImage) {
        if (orientation.equals("horizontal")) return flipHorizontal(inputImage);
        else return flipVertical(inputImage);
    }

    @Override
    public void sanitizeInput(BufferedImage inputImage) {
        if (!(orientation.equals("horizontal") || orientation.equals("vertical")))
            isInvalid = true;
    }
}
