package com.cnu.assignment02;

import java.awt.image.BufferedImage;

public class ResizeOperation extends ImageOperation {
    public Integer height;
    public Integer width;

    @Override
    public void sanitizeInput(BufferedImage inputImage) {
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

    @Override
    public BufferedImage start(BufferedImage inputImage) {
        return ImageUtils.resizeImage(inputImage, width, height);
    }
}
