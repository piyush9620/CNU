package com.cnu.assignment02;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class RotateOperation extends ImageOperation {
    public int degrees;

    public BufferedImage rotateImage(BufferedImage inputImage) {
        int type = inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType();
        BufferedImage operatedImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), type);

        AffineTransform tx = new AffineTransform();
        tx.rotate((double)degrees/180.0*Math.PI, inputImage.getWidth() / 2, inputImage.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BICUBIC);
        operatedImage = op.filter(inputImage, operatedImage);
        return operatedImage;
    }

    @Override
    public BufferedImage start(BufferedImage inputImage) {
        BufferedImage operatedImage = rotateImage(inputImage);
        return operatedImage;
    }

    @Override
    public void sanitizeInput(BufferedImage inputImage) {
        degrees = (degrees + 360)%360;
    }
}
