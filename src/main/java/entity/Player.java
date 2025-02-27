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
    int hasKey = 0, hasBook = 0;

    public Player(GameLoop gl, InputHandler inputHandler) {
        this.gl = gl;
        this.inputHandler = inputHandler;

        screenX = gl.screenWidth / 2 - gl.tileSize / 2;
        screenY = gl.screenHeight / 2 - gl.tileSize / 2;

        bounds = new Rectangle();
        bounds.x = 16;
        bounds.y = 36;
        boundsDefaultX = bounds.x;
        boundsDefaultY = bounds.y;
        bounds.width = 32;
        bounds.height = 24;

        setDefaultValues();
        loadPlayerImages();
    }

    public void setDefaultValues() {
        worldX = (int) ((gl.maxWorldCol / 2.31) * gl.tileSize);
        worldY = (int) ((gl.maxWorldRow / 4.3) * gl.tileSize - ((double) gl.tileSize / gl.scale));

        speed = 8;
        direction = "left";
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
            try {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathPrefix + (i + 1) + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    public void update() {
        if (inputHandler.upPressed || inputHandler.downPressed || inputHandler.leftPressed || inputHandler.rightPressed) {
            if (inputHandler.upPressed) {
                direction = "up";
            } else if (inputHandler.downPressed) {
                direction = "down";
            } else if (inputHandler.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collision = false;
            gl.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objectIndex = gl.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collision) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 6) {
                spriteNumber = (spriteNumber + 1) % sprites[getDirectionIndex()].length;
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gl.object[i].name;
            switch (objectName) {
                case "Key":
                    hasKey++;
                    gl.object[i] = null;
                    System.out.println("Key: " + hasKey);
                    break;
                case "Book":
                    hasBook++;
                    gl.object[i] = null;
                    break;
                case "Chest":
                    if (hasKey > 0) {
                        gl.object[i] = null;
                        hasKey--;
                    }
                    System.out.println("Key: " + hasKey);
                    break;
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
