package com.cnu.assignment02;

import java.awt.image.BufferedImage;

public class NegativeOperation extends ImageOperation {

    private int invertPixel(int p) {
        int a = (p>>24)&0xff;
        int r = (p>>16)&0xff;
        int g = (p>>8)&0xff;
        int b = p&0xff;
        //subtract RGB from 255
        r = 255 - r;
        g = 255 - g;
        b = 255 - b;
        //set new RGB value
        return (a<<24) | (r<<16) | (g<<8) | b;
    }

    private BufferedImage negative(BufferedImage inputImage) {
        int type = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType();
        BufferedImage operatedImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), type);
        for(int y = 0; y < inputImage.getHeight(); y++){
            for(int x = 0; x < inputImage.getWidth(); x++){
                int p = inputImage.getRGB(x,y);
                int q = invertPixel(p);
                operatedImage.setRGB(x, y, q);
            }
        }

        return operatedImage;
    }

    @Override
    public BufferedImage start(BufferedImage inputImage) {
        return negative(inputImage);
    }

    @Override
    public void sanitizeInput(BufferedImage inputImage) {

    }

    @Override
    public int hashCode() {
        return 12;
    }

    @Override
    public boolean equals(Object op) {
        if (op instanceof NegativeOperation) {
            return true;
        }
        return false;
    }
}
