package main;

import java.util.HashMap;
import java.util.Map;

public class LevelGenerator {
    public int width, height;
    Integer[][] map;
    private Map<String, Integer> tile = new HashMap<>();

    enum Tiles {
        STONE,
        PATH
    }

    public LevelGenerator() {
        this.width = 75;
        this.height = 75;
        map = new Integer[width][height];
        getTiles();
        fillMap();
    }

    private void getTiles() {
        tile.put(Tiles.STONE.name(), 1);
        tile.put(Tiles.PATH.name(), 5);
    }

    private void fillMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = tile.get(Tiles.STONE.name());
            }
        }
    }

    public static void main(String[] args) {
        new LevelGenerator();
    }
}
