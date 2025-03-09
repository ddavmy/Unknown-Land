package world;

import main.GameLoop;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class LevelGenerator {
    private final int width, height, maxRoomQuantity;
    private final int[][] map;
    private final Random random;
    private final List<RoomHelper> rooms;
    GameLoop gl;

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

    public LevelGenerator(GameLoop gl, int width, int height, int roomQuantity) {
        this.random = new Random();
        this.width = width;
        this.height = height;
        this.maxRoomQuantity = roomQuantity;
        this.map = new int[height][width];
        this.rooms = new ArrayList<>();
        this.gl = gl;
    }

    public int[][] getGeneratedMap() {
        return this.map;
    }

    public boolean generate() {
        fillMap();
        generateRooms();
        placeRoomsOnMap();
        placeBossAndPlayer();
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

    private void generateRooms() {
        int maxAttempts = maxRoomQuantity * 5;
        int attempt = 0;

        while (attempt < maxAttempts && rooms.size() < maxRoomQuantity) {
            int roomType = random.nextInt(2);
            RoomHelper newRoom = null;

            if (!rooms.isEmpty()) {
                switch (roomType) {
                    case 0 -> newRoom = generateCircularRoom();
                    case 1 -> newRoom = generateRectangularRoom();
                }
            } else {
                newRoom = generateCircularRoom();
            }

            if (!newRoom.overlapsAnyRoom(rooms, newRoom)) {
                rooms.add(newRoom);
            } else {
                attempt++;
            }
        }
    }

    private RoomHelper generateRectangularRoom() {
        int roomWidth = (random.nextInt(2) * 2) + 5;
        int roomHeight = (random.nextInt(2) * 2) + 5;

        int roomX = random.nextInt(width - roomWidth - 2) + 1;
        int roomY = random.nextInt(height - roomHeight - 2) + 1;

        return new RoomHelper(roomX, roomY, roomWidth, roomHeight, RoomHelper.Shapes.RECTANGLE);
    }

    private RoomHelper generateCircularRoom() {
        int radius;
        if (rooms.isEmpty()) {
            radius = random.nextInt(2) + 3;
        } else {
            radius = random.nextInt(4) + 4;
        }

        int centerX = random.nextInt(width - radius * 2 - 2) + radius + 1;
        int centerY = random.nextInt(height - radius * 2 - 2) + radius + 1;

        return new RoomHelper(centerX - radius, centerY - radius, radius * 2, radius * 2, RoomHelper.Shapes.CIRCLE);
    }

    private void placeRoomsOnMap() {
        for (RoomHelper room : rooms) {
            room.placeOnMap(map, TileType.PATH.getValue());
        }
    }

    public void placeBossAndPlayer() {
        RoomHelper bossRoom = null, playerRoom = null;
        int maxRadius = 0;
        double distance, maxDistance = 0;

        for (RoomHelper room : rooms) {
            if (room.type == RoomHelper.Shapes.CIRCLE) {
                int radius = Math.min(room.width, room.height) / 2;
                if (radius > maxRadius) {
                    bossRoom = room;
                    maxRadius = radius;
                }
            }
        }

        assert bossRoom != null;
        for (RoomHelper room : rooms) {
            distance = Math.sqrt(Math.pow(room.x - bossRoom.x, 2) + Math.pow(room.y - bossRoom.y, 2));
            if (distance > maxDistance) {
                playerRoom = room;
                maxDistance = distance;
            }
        }

        int[] center = bossRoom.getCenter();
        map[center[0]][center[1]] = TileType.DIRT.getValue();

        assert playerRoom != null;
        center = playerRoom.getCenter();
        map[center[0]][center[1]] = TileType.WATER.getValue();

        gl.playerX = center[1] * gl.tileSize;
        gl.playerY = center[0] * gl.tileSize;
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
                    if (y == height - 1) break;
                    writer.newLine();
                }
                System.out.println("Map saved successfully to " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
