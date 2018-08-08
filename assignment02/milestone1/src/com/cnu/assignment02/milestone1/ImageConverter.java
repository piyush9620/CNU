package com.cnu.assignment02.milestone1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageConverter {
    private static ImageResolution maintainResolution(ImageResolution originalResolution, ImageResolution requiredResolution) {
        if (requiredResolution.width == 0 && requiredResolution.height == 0) {
            return originalResolution;
        }
        else if (requiredResolution.width == 0) {
            return new ImageResolution((int) Math.round((double)originalResolution.width/(double)originalResolution.height*(double)requiredResolution.height), requiredResolution.height);
        }
        else if (requiredResolution.height == 0) {
            return new ImageResolution(requiredResolution.width, (int) Math.round((double)originalResolution.height/(double)originalResolution.width*(double)requiredResolution.width));
        }
        else return requiredResolution;
    }


    public static BufferedImage resizeImage(BufferedImage originalImage, ImageResolution requiredResolution, int type){
        ImageResolution originalResolution = new ImageResolution(originalImage.getWidth(), originalImage.getHeight());
        requiredResolution = maintainResolution(originalResolution, requiredResolution);

        BufferedImage resizedImage = new BufferedImage(requiredResolution.width, requiredResolution.height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, requiredResolution.width, requiredResolution.height, null);
        g.dispose();

        return resizedImage;
    }
}
