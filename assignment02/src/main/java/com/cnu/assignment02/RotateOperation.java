package com.cnu.assignment02;

import java.awt.image.BufferedImage;

public class RotateOperation extends ImageOperation {
    public int degrees;

    public BufferedImage rotateClockWise(BufferedImage inputImage) {
        int width  = inputImage.getWidth();
        int height = inputImage.getHeight();
        int type = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType();
        BufferedImage   operatedImage = new BufferedImage( height, width, type );

        for( int i=0 ; i < width ; i++ )
            for( int j=0 ; j < height ; j++ )
                operatedImage.setRGB( height-1-j, i, inputImage.getRGB(i,j) );

        return operatedImage;
    }

    @Override
    public BufferedImage start(BufferedImage inputImage) {
        BufferedImage operatedImage = inputImage;
        for (int i=0; i < (degrees/90)%4; i++) {
            operatedImage = rotateClockWise(operatedImage);
        }

        return operatedImage;
    }

    @Override
    public void sanitizeInput(BufferedImage inputImage) {
        degrees = (degrees + 360)/degrees;
    }
}
