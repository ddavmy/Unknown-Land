package entity;

import main.GameLoop;
import main.DrawingUtils;
import main.InputHandler;
import sprite.SheetManager;
import sprite.SpriteData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GameLoop gl;
    InputHandler inputHandler;
    SheetManager sheetManager;
    SpriteData[][] spriteData;
    BufferedImage[][] frames;

    String[] directions = {"up", "down", "left", "right"};

    public final int screenX;
    public final int screenY;
    int hasKey = 0, hasBook = 0;

    public Player(GameLoop gl, InputHandler inputHandler) {
        this.gl = gl;
        this.inputHandler = inputHandler;
        this.sheetManager = new SheetManager();
        this.frames = new BufferedImage[directions.length][];

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

        speed = 4;
        direction = "left";
    }

    public void loadPlayerImages() {
        try {
            BufferedImage spriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/knight.png")));

            this.spriteData = new SpriteData[directions.length][];

            for (int i = 0; i < directions.length; i++) {
                spriteData[i] = sheetManager.SpriteLoader("/sprites.json", "player1", "walk", directions[i]);

                if (spriteData[i] != null) {
                    frames[i] = new BufferedImage[spriteData[i].length];

                    for (int j = 0; j < spriteData[i].length; j++) {
                        frames[i][j] = sheetManager.grabImage(spriteSheet, spriteData[i][j].x, spriteData[i][j].y, spriteData[i][j].w, spriteData[i][j].h
                        );
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                spriteNumber = (spriteNumber + 1) % frames[getDirectionIndex()].length;
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
        BufferedImage image = frames[getDirectionIndex()][spriteNumber];
        boolean isFlipped = spriteData[getDirectionIndex()][spriteNumber].flipped;

        DrawingUtils.drawFlippedSprite(g2, image, screenX, screenY, gl.tileSize, gl.tileSize, isFlipped);
    }

    private int getDirectionIndex() {
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].equals(direction)) return i;
        }
        return 0;
    }
}
