package com.cnu.assignment02;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {
    public static BufferedImage resizeImage(BufferedImage inputImage, int width, int height) {
        int type = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType();
        BufferedImage operatedImage = new BufferedImage(width, height, type);
        Graphics2D g = operatedImage.createGraphics();
        g.drawImage(inputImage, 0, 0, width, height, null);
        g.dispose();
        return operatedImage;
    }
}
