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
        mapTileNumber = new int[gl.maxWorldCol][gl.maxWorldRow];

        getTileImage();
        loadMap("/maps/world02.txt");
    }
//    }

    public void getTileImage() {
        String[] tilePaths = {
                "/tiles/dirt.png",
                "/tiles/stone.png",
                "/tiles/water.png",
                "/tiles/grass.png",
                "/tiles/tree.png",
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

            while (col < gl.maxWorldCol && row < gl.maxWorldRow) {
                String line = bufferedReader.readLine();

                while (col < gl.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int number = Integer.parseInt(numbers[col]);
                    mapTileNumber[col][row] = number;
                    col++;
                }
                if (col == gl.maxWorldCol) {
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
        int worldCol = 0, worldRow = 0;

        while (worldCol < gl.maxWorldCol && worldRow < gl.maxWorldRow) {
            int tileNumber = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gl.tileSize;
            int worldY = worldRow * gl.tileSize;
            int screenX = worldX - gl.player.worldX + gl.player.screenX;
            int screenY = worldY - gl.player.worldY + gl.player.screenY;

            g2.drawImage(tile[tileNumber].image, screenX, screenY, gl.tileSize, gl.tileSize, null);
            worldCol++;

            if (worldCol == gl.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
