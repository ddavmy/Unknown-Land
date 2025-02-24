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
        loadMap("/maps/map01.txt");
    }
//    }

    public void getTileImage() {
        String[] tilePaths = {
                "/tiles/dirt.png",
                "/tiles/stone.png",
                "/tiles/water.png",
                "/tiles/grass.png",
                "/tiles/stonePath.png",
        };

        try {
            for (int i = 0; i < tilePaths.length; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(tilePaths[i])));
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(mapPath);
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
