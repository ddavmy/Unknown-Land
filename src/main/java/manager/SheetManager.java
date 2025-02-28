package manager;

import java.awt.image.BufferedImage;

public class SheetManager {

    public BufferedImage sheet;

    public BufferedImage grabImage(int x, int y, int w, int h) {

        return sheet.getSubimage(x, y, w, h);
    }
}
