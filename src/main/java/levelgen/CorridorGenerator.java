package levelgen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CorridorGenerator {
    List<RoomHelper> rooms;

    public void generate(List<RoomHelper> rooms) {
        this.rooms = rooms;

        generateMST();
    }

    private List<Edge> generateMST() {
        List<Edge> edges = new ArrayList<>();
        List<Edge> mst = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j  < rooms.size(); j++) {
                RoomHelper roomSrc = rooms.get(i);
                RoomHelper roomDst = rooms.get(j);
                int[] centerSrc = roomSrc.getCenter();
                int[] centerDst = roomDst.getCenter();
                int distance = calculateDistance(centerSrc, centerDst);
                edges.add(new Edge(roomSrc, roomDst, distance));
            }
        }

        edges.sort(Comparator.comparingInt(edge -> edge.distance));

        UnionFind unionFind = new UnionFind(rooms.size());
        for (Edge edge : edges) {
            int roomSrcIndex = rooms.indexOf(edge.roomSrc);
            int roomDstIndex = rooms.indexOf(edge.roomDst);
            if (unionFind.find(roomSrcIndex) != unionFind.find(roomDstIndex)) {
                unionFind.union(roomSrcIndex, roomDstIndex);
                mst.add(edge);
            }
        }

        return mst;
    }

    private int calculateDistance(int[] center1, int[] center2) {
        return Math.abs(center1[0] - center2[0]) + Math.abs(center1[1] - center2[1]);
    }
}
