package main;

import javax.swing.*;

public class GameLoop extends JPanel {
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 4;

    final int tileSize =  originalTileSize * scale; // 64x64 tile
    final int maxScreenCol = 20;
    final int maxScreenRow = 16;
    final int screenWidth = tileSize * maxScreenCol; // 1280 pixels
    final int screenHeight = tileSize * maxScreenRow; // 1024 pixels
}
