package main;

import javax.swing.*;
import java.awt.*;

public class GameLoop extends JPanel implements Runnable {
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 4;

    final int tileSize = originalTileSize * scale; // 64x64 tile
    final int maxScreenCol = 20;
    final int maxScreenRow = 16;
    final int screenWidth = tileSize * maxScreenCol; // 1280 pixels
    final int screenHeight = tileSize * maxScreenRow; // 1024 pixels

    Thread gameThread;

    public GameLoop() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread.isAlive()) {
            System.out.println("Game loop started");
        }
    }
}
