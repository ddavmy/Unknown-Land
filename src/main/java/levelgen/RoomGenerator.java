package levelgen;

import main.GameLoop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomGenerator {

    private final Random random;
    private final List<RoomHelper> rooms;
    private final LevelGenerator levelGen;

    public RoomGenerator(LevelGenerator levelGen) {
        this.levelGen = levelGen;
        random = new Random();
        rooms = new ArrayList<>();
    }

    public void generate(GameLoop gl) {
        generateRooms();
        placeRoomsOnMap();
        placeBossAndPlayer(gl);
    }

    private void generateRooms() {
        int maxAttempts = levelGen.maxRoomQuantity * 5;
        int attempt = 0;

        while (attempt < maxAttempts && rooms.size() < levelGen.maxRoomQuantity) {
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

        int roomX = random.nextInt(levelGen.width - roomWidth - 2) + 1;
        int roomY = random.nextInt(levelGen.height - roomHeight - 2) + 1;

        return new RoomHelper(roomX, roomY, roomWidth, roomHeight, RoomHelper.Shapes.RECTANGLE);
    }

    private RoomHelper generateCircularRoom() {
        int radius;
        if (rooms.isEmpty()) {
            radius = random.nextInt(2) + 3;
        } else {
            radius = random.nextInt(4) + 4;
        }

        int centerX = random.nextInt(levelGen.width - radius * 2 - 2) + radius + 1;
        int centerY = random.nextInt(levelGen.height - radius * 2 - 2) + radius + 1;

        return new RoomHelper(centerX - radius, centerY - radius, radius * 2, radius * 2, RoomHelper.Shapes.CIRCLE);
    }

    private void placeRoomsOnMap() {
        for (RoomHelper room : rooms) {
            room.placeOnMap(levelGen.map, LevelGenerator.TileType.PATH.getValue());
        }
    }

    private void placeBossAndPlayer(GameLoop gl) {
        RoomHelper bossRoom = null, playerRoom = null;
        int maxRadius = 0;
        double maxDistance = 0;

        for (RoomHelper room : rooms) {
            if (room.type == RoomHelper.Shapes.CIRCLE) {
                int radius = Math.min(room.width, room.height) / 2;
                if (radius > maxRadius) {
                    bossRoom = room;
                    maxRadius = radius;
                }
            }
        }

        if (bossRoom == null) {
            throw new IllegalStateException("Boss room should be placed first");
        }

        bossRoom.bossRoom = true;

        int bossX = bossRoom.x, bossY = bossRoom.y;
        for (RoomHelper room : rooms) {
            int dx = room.x - bossX;
            int dy = room.y - bossY;
            int distanceSquared = dx * dx + dy * dy;

            if (distanceSquared > maxDistance) {
                playerRoom = room;
                maxDistance = distanceSquared;
            }
        }

        int[] bossCenter = bossRoom.getCenter();
        levelGen.map[bossCenter[0]][bossCenter[1]] = LevelGenerator.TileType.DIRT.getValue();

        if (playerRoom == null) {
            throw new IllegalStateException("Player room should be placed first");
        }

        int[] playerCenter = playerRoom.getCenter();
        levelGen.map[playerCenter[0]][playerCenter[1]] = LevelGenerator.TileType.WATER.getValue();

        gl.playerX = playerCenter[1] * gl.tileSize;
        gl.playerY = playerCenter[0] * gl.tileSize;
    }

    public List<RoomHelper> getRooms() {
        return rooms;
    }
}
