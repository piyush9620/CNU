package com.cnu.assignment02;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.image.BufferedImage;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=ResizeOperation.class, name="resize"),
        @JsonSubTypes.Type(value=ScaleOperation.class, name="scale"),
        @JsonSubTypes.Type(value=RotateOperation.class, name="rotate"),
        @JsonSubTypes.Type(value=NegativeOperation.class, name="negative"),
        @JsonSubTypes.Type(value=FlipOperation.class, name="flip")
})
public abstract class ImageOperation {
    protected Boolean isInvalid = false;

    public abstract BufferedImage start(BufferedImage inputImage);

    public abstract void sanitizeInput(BufferedImage inputImage);

    public Boolean isValid() {
        return !isInvalid;
    }
}