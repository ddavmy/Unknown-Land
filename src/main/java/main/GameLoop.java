package main;

import entity.Player;
import object.SuperObject;
import world.LevelGenerator;
import world.TileManager;

import javax.swing.*;
import java.awt.*;

public class GameLoop extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    public final int scale = 4;

    public final int tileSize = originalTileSize * scale; // 64x64 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol; // 1280 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 1024 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 80;
    public final int maxWorldRow = 80;
    final int maxRoomQuantity = 20;

    // FPS
    int FPS = 60;

    // SYSTEM
    LevelGenerator levelGenerator = new LevelGenerator(maxWorldCol, maxWorldRow, maxRoomQuantity);
    TileManager tileManager = new TileManager(this);
    InputHandler inputHandler = new InputHandler();
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, inputHandler);
    public SuperObject[] object = new SuperObject[16];

    // SOUND
    private static final int MUSIC_BACKGROUND = 0;
    private static final int SOUND_CHEST = 1;
    private static final int SOUND_FOOTSTEPS = 2;
    private static final int SOUND_PICKUP = 3;

    public GameLoop() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        playMusic(0, -7.0f);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000.0 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread.isAlive()) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TILE
        tileManager.draw(g2);

        // OBJECT
        for (int i = 0; i < object.length; i++) {
            if (object[i] != null) {
                object[i].draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        g2.dispose();
    }

    public void playMusic(int index, float volume) {
        music.setFile(index, volume);
        music.play(index);
        music.loop(index);
    }

    public void stopMusic() {
        music.stop(MUSIC_BACKGROUND);
    }

    public void playSoundEffect(int index) {
        soundEffect.setFile(index, 1);
        soundEffect.play(index);
    }

    public boolean isFootstepsPlaying() {
        return soundEffect.isPlaying(SOUND_FOOTSTEPS);
    }

    public void startFootsteps() {
        if (!isFootstepsPlaying()) {
            playSoundEffect(SOUND_FOOTSTEPS);
        }
    }

    public void stopFootsteps() {
        if (isFootstepsPlaying()) {
            soundEffect.stop(SOUND_FOOTSTEPS);
        }
    }
}
