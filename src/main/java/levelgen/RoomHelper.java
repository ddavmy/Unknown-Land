package levelgen;

import java.util.List;

public class RoomHelper {
    public final int x, y, width, height;
    Shapes type;
    public boolean bossRoom;

    public enum Shapes {
        RECTANGLE,
        CIRCLE
    }

    public RoomHelper(int x, int y, int width, int height, Shapes type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public boolean overlapsAnyRoom(List<RoomHelper> rooms, RoomHelper newRoom) {
        for (RoomHelper existingRoom : rooms) {
            if (roomsOverlap(newRoom, existingRoom)) {
                return true;
            }
        }

        return false;
    }

    private boolean roomsOverlap(RoomHelper room1, RoomHelper room2) {
        int buffer = 2;

        return !(room1.x + room1.width + buffer <= room2.x ||
                room2.x + room2.width + buffer <= room1.x ||
                room1.y + room1.height + buffer <= room2.y ||
                room2.y + room2.height + buffer <= room1.y);
    }

    public void placeOnMap(int[][] map, int tileType) {
        switch (type) {
            case RECTANGLE -> placeRectangularRoom(map, tileType);
            case CIRCLE -> placeCircularRoom(map, tileType);
        }
    }

    private void placeRectangularRoom(int[][] map, int tileType) {
        for (int dy = 0; dy < height; dy++) {
            for (int dx = 0; dx < width; dx++) {
                int mapX = x + dx;
                int mapY = y + dy;

                if (mapX >= 0 && mapX < map[0].length && mapY >= 0 && mapY < map.length) {
                    map[mapY][mapX] = tileType;
                }
            }
        }
    }

    private void placeCircularRoom(int[][] map, int tileType) {
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int radius = Math.min(width, height) / 2;

        for (int dy = -radius; dy <= radius; dy++) {
            for (int dx = -radius; dx <= radius; dx++) {
                if (dx * dx + dy * dy <= radius * radius) {
                    int mapX = centerX + dx;
                    int mapY = centerY + dy;

                    if (mapX >= 0 && mapX < map[0].length && mapY >= 0 && mapY < map.length) {
                        map[mapY][mapX] = tileType;
                    }
                }
            }
        }
    }

    public int[] getCenter() {
        return new int[] {y + height / 2, x + width / 2};
    }

    public boolean isBossRoom() {
        return bossRoom;
    }
}
