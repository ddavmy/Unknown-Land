package world;

import main.GameLoop;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class TileManager {
    GameLoop gl;
    public Tile[] tile;
    public int[][] mapTileNumber;

    public TileManager(GameLoop gl) {
        this.gl = gl;

        tile = new Tile[10];
        mapTileNumber = new int[gl.maxWorldCol][gl.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {
        // NAME OF TILE AND BOOLEAN FOR IT'S COLLISION
        Map<String, Boolean> tileData = new LinkedHashMap<>();
        tileData.put("dirt", false);
        tileData.put("stone", true);
        tileData.put("water", true);
        tileData.put("grass", false);
        tileData.put("tree", true);
        tileData.put("stonePath", false);

        int i = 0;
        for (Map.Entry<String, Boolean> entry : tileData.entrySet()) {
            tile[i] = new Tile();
            try {
                tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + entry.getKey() + ".png")));
                tile[i].collision = entry.getValue();
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
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

            if (worldX + gl.tileSize > gl.player.worldX - gl.player.screenX &&
                    worldX - gl.tileSize < gl.player.worldX + gl.player.screenX &&
                    worldY + gl.tileSize > gl.player.worldY - gl.player.screenY &&
                    worldY - gl.tileSize < gl.player.worldY + gl.player.screenY) {
                g2.drawImage(tile[tileNumber].image, screenX, screenY, gl.tileSize, gl.tileSize, null);
            }
            worldCol++;

            if (worldCol == gl.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
