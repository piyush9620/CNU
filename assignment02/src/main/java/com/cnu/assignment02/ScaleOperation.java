package com.cnu.assignment02;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScaleOperation extends ImageOperation {
    public Float height;
    public Float width;
    private int heightInt;
    private int widthInt;

    @Override
    public void sanitizeInput(BufferedImage inputImage) {
        if (width == null && height == null) {
            width = 1f;
            height = 1f;
        }
        else if (width == null) {
            width = height;
        }
        else if (height == null) {
            height = width;
        }

        widthInt = (int) (width * inputImage.getWidth());
        heightInt = (int) (height * inputImage.getHeight());

        if (height == 0 || width == 0) {
            isInvalid = true;
        }
    }

    @Override
    public BufferedImage start(BufferedImage inputImage) {
        return ImageUtils.resizeImage(inputImage, widthInt, heightInt);
    }
    @Override
    public int hashCode() {
        return (int) (width*31 + height);
    }

    @Override
    public boolean equals(Object op) {
        if (op instanceof ScaleOperation) {
            ScaleOperation sp = (ScaleOperation) op;
            return sp.height == height && sp.width == width;
        }
        return false;
    }
}
