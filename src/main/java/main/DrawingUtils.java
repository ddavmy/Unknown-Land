package main;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class DrawingUtils {
    public static void drawFlippedSprite(Graphics2D g2, BufferedImage image,
                                         int x, int y, int width, int height,
                                         boolean flipped) {
        if (flipped) {
            AffineTransform originalTransform = g2.getTransform();

            AffineTransform flip = new AffineTransform();
            flip.translate(x + width, y);
            flip.scale(-1, 1);
            g2.setTransform(flip);

            g2.drawImage(image, 0, 0, width, height, null);

            g2.setTransform(originalTransform);
        } else {
            g2.drawImage(image, x, y, width, height, null);
        }
    }
}
