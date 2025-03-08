package world;

import java.util.List;

public class RoomHelper {
    public int x, y, width, height;
    public String type;

    public RoomHelper(int x, int y, int width, int height, String type) {
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

    public void placeOnMap(Integer[][] map, int tileType) {
        switch (type) {
            case "rectangle" -> placeRectangularRoom(map, tileType);
            case "circle" -> placeCircularRoom(map, tileType);
        }
    }

    private void placeRectangularRoom(Integer[][] map, int tileType) {
        for (int dx = 0; dx < height; dx++) {
            for (int dy = 0; dy < width; dy++) {
                int mapX = x + dx;
                int mapY = y + dy;

                if (mapX >= 0 && mapX < map[0].length && mapY >= 0 && mapY < map.length) {
                    map[mapY][mapX] = tileType;
                }
            }
        }
    }

    private void placeCircularRoom(Integer[][] map, int tileType) {
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int radius = Math.min(width, height) / 2;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                int mapX = centerX + dx;
                int mapY = centerY + dy;

                if (mapX >= 0 && mapX < map[0].length && mapY >= 0 && mapY < map.length) {
                    map[mapY][mapX] = tileType;
                }
            }
        }
    }
}
