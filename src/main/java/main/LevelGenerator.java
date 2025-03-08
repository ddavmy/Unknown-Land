package main;

import java.util.*;

public class LevelGenerator {
    public static int width, height, roomQuantity;
    private static Integer[][] map;
    private final Map<String, Integer> tile = new HashMap<>();

    enum Tiles {
        STONE,
        PATH
    }

    public LevelGenerator() {
        width = 40;
        height = 16;
        map = new Integer[width][height];
        getTiles();
        fillMap();
    }

    private void getTiles() {
        tile.put(Tiles.STONE.name(), 1);
        tile.put(Tiles.PATH.name(), 5);
    }

    private void fillMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[j][i] = tile.get(Tiles.STONE.name());
            }
        }
    }

    public static void main(String[] args) {
        new LevelGenerator();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (map[j][i] == 1) {
                    System.out.print("■");
                } else if (map[j][i] == 5) {
                    System.out.print("□");
                } else {
                    System.out.println("☒");
                }
            }
            System.out.println();
        }
    }
}
