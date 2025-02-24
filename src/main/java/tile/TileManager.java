package tile;

import main.GameLoop;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TileManager {
    GameLoop gl;
    Tile[] tile;

    public TileManager(GameLoop gl) {
        this.gl = gl;
        tile = new Tile[10];
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/dirt.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(tile[0].image, 0, 0, gl.tileSize, gl.tileSize, null);
        g2.drawImage(tile[1].image, 0, 0, gl.tileSize, gl.tileSize, null);
        g2.drawImage(tile[2].image, 64, 0, gl.tileSize, gl.tileSize, null);
    }
}
