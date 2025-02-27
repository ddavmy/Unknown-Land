package object;

import main.GameLoop;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle bounds = new Rectangle(0, 0, 64, 64);
    public int boundsDefaultX = 0;
    public int boundsDefaultY = 0;

    public void draw(Graphics2D g2, GameLoop gl) {
        int screenX = worldX - gl.player.worldX + gl.player.screenX;
        int screenY = worldY - gl.player.worldY + gl.player.screenY;

        int tileSize = gl.tileSize;

        if (name.equals("Key") || name.equals("Book")) {
            tileSize /= 1.6;
        }

        if (worldX + gl.tileSize > gl.player.worldX - gl.player.screenX &&
                worldX - gl.tileSize < gl.player.worldX + gl.player.screenX &&
                worldY + gl.tileSize > gl.player.worldY - gl.player.screenY &&
                worldY - gl.tileSize < gl.player.worldY + gl.player.screenY) {
            g2.drawImage(image, screenX, screenY, tileSize, tileSize, null);
        }
    }
}
