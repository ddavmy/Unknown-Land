package levelgen;

public class Edge {
    RoomHelper roomSrc, roomDst;
    public int distance;

    public Edge(RoomHelper roomSrc, RoomHelper roomDst, int distance) {
        this.roomSrc = roomSrc;
        this.roomDst = roomDst;
        this.distance = distance;
    }
}
