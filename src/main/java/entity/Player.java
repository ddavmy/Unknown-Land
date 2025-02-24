package entity;

import main.GameLoop;
import main.InputHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GameLoop gl;
    InputHandler inputHandler;
    BufferedImage[][] sprites = new BufferedImage[4][];

    String[] directions = {"up", "down", "left", "right"};

    public final int screenX;
    public final int screenY;

    public Player(GameLoop gl, InputHandler inputHandler) {
        this.gl = gl;
        this.inputHandler = inputHandler;

        screenX = gl.screenWidth / 2 - gl.tileSize / 2;
        screenY = gl.screenHeight / 2 - gl.tileSize / 2;

        setDefaultValues();
        loadPlayerImages();
    }

    public void setDefaultValues() {
        worldX = (gl.maxWorldCol / 2) * gl.tileSize;
        worldY = (gl.maxWorldRow / 2) * gl.tileSize - (gl.tileSize / gl.scale);

        speed = 4;
        direction = "down";
    }

    public void loadPlayerImages() {
        for (int i = 0; i < directions.length; i++) {
            String dir = directions[i];
            sprites[i] = loadDirectionSprites("/player/walk/" + dir + "_");
        }
    }

    private BufferedImage[] loadDirectionSprites(String pathPrefix) {
        BufferedImage[] images;
        int frameCount = 6;
        images = new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            try  {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathPrefix + (i + 1) + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    public void update() {
        if (inputHandler.upPressed  || inputHandler.downPressed || inputHandler.leftPressed || inputHandler.rightPressed) {
            if (inputHandler.upPressed) {
                direction = "up";
                worldY -= speed;
            }
            else if (inputHandler.downPressed) {
                direction = "down";
                worldY += speed;
            }
            else if (inputHandler.leftPressed) {
                direction = "left";
                worldX -= speed;
            }

            else {
                direction = "right";
                worldX += speed;
            }

            spriteCounter++;
            if (spriteCounter > 6) {
                spriteNumber = (spriteNumber + 1) % sprites[getDirectionIndex()].length;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = sprites[getDirectionIndex()][spriteNumber];
        g2.drawImage(image, screenX, screenY, gl.tileSize, gl.tileSize, null);
    }

    private int getDirectionIndex() {
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].equals(direction)) return i;
        }
        return 0;
    }
}
