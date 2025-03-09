package world;

import java.util.*;

public class LevelGenerator {
    private final int width, height, maxRoomQuantity;
    private final int[][] map;
    private final Random random;
    private final List<RoomHelper> rooms;

    enum TileType {
        STONE(1),
        PATH(5);

        private final int value;

        TileType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public LevelGenerator(int width, int height, int roomQuantity) {
        this.random = new Random();
        this.width = width;
        this.height = height;
        this.maxRoomQuantity = roomQuantity;
        this.map = new int[height][width];
        this.rooms = new ArrayList<>();
        generate();

        System.out.println();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] == 1) {
                    System.out.print("■ ");
                } else if (map[y][x] == 5) {
                    System.out.print("□ ");
                } else {
                    System.out.println("☒ ");
                }
            }
            System.out.println();
        }
    }

    public void generate() {
        fillMap();
        generateRooms();
        placeRoomsOnMap();
    }

    private void fillMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x] = TileType.STONE.getValue();
            }
        }
    }

    private void generateRooms() {
        int maxAttempts = maxRoomQuantity * 3;
        int attempt = 0;

        while (attempt < maxAttempts && rooms.size() < maxRoomQuantity) {
            int roomType = random.nextInt(2);
            RoomHelper newRoom = null;

            switch (roomType) {
                case 0 -> newRoom = generateCircularRoom();
                case 1 -> newRoom = generateRectangularRoom();
            }

            if (!newRoom.overlapsAnyRoom(rooms, newRoom)) {

                switch (roomType) {
                    case 0 -> System.out.println("Generating circular room");
                    case 1 -> System.out.println("Generating rectangular room");
                }

                rooms.add(newRoom);
            } else {
                attempt++;
            }
        }
    }

    private RoomHelper generateRectangularRoom() {
        int roomWidth = random.nextInt(3) + 4;
        int roomHeight = random.nextInt(3) + 4;

        int roomX = random.nextInt(width - roomWidth - 2) + 1;
        int roomY = random.nextInt(height - roomHeight - 2) + 1;

        return new RoomHelper(roomX, roomY, roomWidth, roomHeight, RoomHelper.Shapes.RECTANGLE);
    }

    private RoomHelper generateCircularRoom() {
        int radius = random.nextInt(4) + 3;

        int centerX = random.nextInt(width - radius * 2 - 2) + radius + 1;
        int centerY = random.nextInt(height - radius * 2 - 2) + radius + 1;

        return new RoomHelper(centerX - radius, centerY - radius, radius * 2, radius * 2, RoomHelper.Shapes.CIRCLE);
    }

    private void placeRoomsOnMap() {
        for (RoomHelper room : rooms) {
            room.placeOnMap(map, TileType.PATH.getValue());
        }
    }
}
