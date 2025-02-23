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
        if (inputHandler.downPressed) {
            direction = "down";
            y += speed;
        }
        if (inputHandler.leftPressed) {
            direction = "left";
            x -= speed;
        }
        if (inputHandler.rightPressed) {
            direction = "right";
            x += speed;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" -> up1;
            case "down" -> down1;
            case "left" -> left1;
            case "right" -> right1;
            default -> null;
        };
        g2.drawImage(image, x, y, gl.tileSize, gl.tileSize, null);
    }
}
