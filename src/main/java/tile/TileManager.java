package tile;

import main.GameLoop;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GameLoop gl;
    Tile[] tile;
    int mapTileNumber[][];

    public TileManager(GameLoop gl) {
        this.gl = gl;
        tile = new Tile[10];
        mapTileNumber = new int[gl.maxScreenCol][gl.maxScreenRow];
        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/dirt.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/stone.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/maps/map01.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));

            int col = 0, row = 0;

            while (col < gl.maxScreenCol && row < gl.maxScreenRow) {
                String line = bufferedReader.readLine();

                while (col < gl.maxScreenCol) {
                    String[] numbers = line.split(" ");
                    int number = Integer.parseInt(numbers[col]);
                    mapTileNumber[col][row] = number;
                    col++;
                }
                if (col == gl.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0, row = 0, x = 0, y = 0;

        while (col < gl.maxScreenCol && row < gl.maxScreenRow) {
            int tileNumber = mapTileNumber[col][row];

            g2.drawImage(tile[tileNumber].image, x, y, gl.tileSize, gl.tileSize, null);
            col++;
            x += gl.tileSize;

            if (col == gl.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gl.tileSize;
            }
        }
    }
}
