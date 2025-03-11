package levelgen;

import main.GameLoop;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LevelGenerator {
    public final int width, height, maxRoomQuantity;
    public final int[][] map;
    private final GameLoop gl;

    enum TileType {
        DIRT(0),
        STONE(1),
        WATER(3),
        PATH(5);

        private final int value;

        TileType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public LevelGenerator(GameLoop gl, int width, int height, int maxRoomQuantity) {
        this.width = width;
        this.height = height;
        this.maxRoomQuantity = maxRoomQuantity;
        this.map = new int[height][width];
        this.gl = gl;
    }

    public int[][] getGeneratedMap() {
        return this.map;
    }

    public boolean generate() {
        RoomGenerator roomGen = new RoomGenerator(this);
        CorridorGenerator corridorGenerator = new CorridorGenerator(this);

        fillMap();
        roomGen.generate(gl);
        List<RoomHelper> rooms = roomGen.getRooms();
        corridorGenerator.generate(rooms);
        writeToFile();

        return true;
    }

    private void fillMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x] = TileType.STONE.getValue();
            }
        }
    }

    private void writeToFile() {
        String resourceDirectory = "src/main/resources/maps";
        String filePath = resourceDirectory + "/world02.txt";

        try {
            Files.createDirectories(Paths.get(resourceDirectory));
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        writer.write(map[y][x] + " ");
                    }
                    writer.newLine();
                }
                System.out.println("Map saved successfully to " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Could not save map: " + e.getMessage());
        }
    }
}
