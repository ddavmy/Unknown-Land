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

    public Player(GameLoop gl, InputHandler inputHandler) {
        this.gl = gl;
        this.inputHandler = inputHandler;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 600;
        y = 500;
        speed = 4;
        direction = "up";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/up_2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/up_3.png")));
            up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/up_4.png")));
            up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/up_5.png")));
            up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/up_6.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/down_2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/down_3.png")));
            down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/down_4.png")));
            down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/down_5.png")));
            down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/down_6.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/left_2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/left_3.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/left_4.png")));
            left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/left_5.png")));
            left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/left_6.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/right_2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/right_3.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/right_4.png")));
            right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/right_5.png")));
            right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk/right_6.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (inputHandler.upPressed) {
            direction = "up";
            y -= speed;
        }
        else if (inputHandler.downPressed) {
            direction = "down";
            y += speed;
        }
        else if (inputHandler.leftPressed) {
            direction = "left";
            x -= speed;
        }
        else if (inputHandler.rightPressed) {
            direction = "right";
            x += speed;
        }

        spriteCounter++;
        if (spriteCounter > 6) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 3;
            } else if (spriteNumber == 3) {
                spriteNumber = 4;
            } else if (spriteNumber == 4) {
                spriteNumber = 5;
            } else if (spriteNumber == 5) {
                spriteNumber = 6;
            } else if (spriteNumber == 6) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNumber == 1) {
                    image = up1;
                }
                if (spriteNumber == 2) {
                    image = up2;
                }
                if (spriteNumber == 3) {
                    image = up3;
                }
                if (spriteNumber == 4) {
                    image = up4;
                }
                if (spriteNumber == 5) {
                    image = up5;
                }
                if (spriteNumber == 6) {
                    image = up6;
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = down1;
                }
                if (spriteNumber == 2) {
                    image = down2;
                }
                if (spriteNumber == 3) {
                    image = down3;
                }
                if (spriteNumber == 4) {
                    image = down4;
                }
                if (spriteNumber == 5) {
                    image = down5;
                }
                if (spriteNumber == 6) {
                    image = down6;
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = left1;
                }
                if (spriteNumber == 2) {
                    image = left2;
                }
                if (spriteNumber == 3) {
                    image = left3;
                }
                if (spriteNumber == 4) {
                    image = left4;
                }
                if (spriteNumber == 5) {
                    image = left5;
                }
                if (spriteNumber == 6) {
                    image = left6;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                }
                if (spriteNumber == 2) {
                    image = right2;
                }
                if (spriteNumber == 3) {
                    image = right3;
                }
                if (spriteNumber == 4) {
                    image = right4;
                }
                if (spriteNumber == 5) {
                    image = right5;
                }
                if (spriteNumber == 6) {
                    image = right6;
                }
        }
        g2.drawImage(image, x, y, gl.tileSize, gl.tileSize, null);
    }
}
