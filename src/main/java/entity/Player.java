package entity;

import main.GameLoop;
import main.InputHandler;

import java.awt.*;

public class Player extends Entity {

    GameLoop gl;
    InputHandler inputHandler;

    public Player(GameLoop gl, InputHandler inputHandler) {
        this.gl = gl;
        this.inputHandler = inputHandler;
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 600;
        y = 500;
        speed = 4;
    }

    public void update() {
        if (inputHandler.upPressed) {
            y -= speed;
        }
        if (inputHandler.downPressed) {
            y += speed;
        }
        if (inputHandler.leftPressed) {
            x -= speed;
        }
        if (inputHandler.rightPressed) {
            x += speed;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, gl.tileSize, gl.tileSize);
    }
}
