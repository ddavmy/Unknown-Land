package sprite;

import java.awt.image.BufferedImage;

public class SpriteData {
    public int x, y, w, h;
    public BufferedImage image;

    public SpriteData(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
