package world;

import java.util.*;

public class LevelGenerator {
    private int width, height, roomQuantity;
    private Integer[][] map;
    private final Map<String, Integer> tile = new HashMap<>();
    private final Random random;
    private List<RoomHelper> rooms;

    enum Tiles {
        STONE,
        PATH
    }

    public LevelGenerator(int width, int height, int roomQuantity) {
        this.random = new Random();
        this.width = width;
        this.height = height;
        this.roomQuantity = roomQuantity;
        this.map = new Integer[width][height];
        this.rooms = new ArrayList<>();
        generate();

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

    public void generate() {
        getTiles();
        fillMap();
        generateRooms();
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

    private void generateRooms() {
        for (int i = 0; i < roomQuantity; i++) {
            int roomType = random.nextInt(2);

            RoomHelper newroom = null;

            switch (roomType) {
                case 0 -> newroom = generateCircularRoom();
                case 1 -> newroom = generateRectangularRoom();
            }

            rooms.add(newroom);
        }
    }

    private RoomHelper generateRectangularRoom() {
        System.out.println("Generating rectangular room");
        int roomWidth = random.nextInt(3) + 4;
        int roomHeight = random.nextInt(3) + 4;
        int roomX = random.nextInt(width - roomWidth - 2) + 1;
        int roomY = random.nextInt(height - roomHeight - 2) + 1;

        return new RoomHelper(roomX, roomY, roomWidth, roomHeight, "rectangle");
    }

    private RoomHelper generateCircularRoom() {
        System.out.println("Generating circular room");
        int radius = random.nextInt(4) + 3;
        int centerX = random.nextInt(width - radius * 2 - 2) + radius + 1;
        int centerY = random.nextInt(height - radius * 2 - 2) + radius + 1;

        return new RoomHelper(centerX - radius, centerY - radius, radius * 2, radius * 2, "circle");
    }
}
