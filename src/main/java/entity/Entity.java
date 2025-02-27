package entity;

import java.awt.*;

public class Entity {
    public int worldX, worldY;
    public int speed;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle bounds;
    public boolean collision = false;
}
