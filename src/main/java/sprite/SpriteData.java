package sprite;

import java.awt.image.BufferedImage;

public class SpriteData {
    public int x, y, w, h;
    public boolean flipped;

    public SpriteData(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.flipped = false;
    }

    public SpriteData(int x, int y, int w, int h, boolean flipped) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.flipped = flipped;
    }
}
