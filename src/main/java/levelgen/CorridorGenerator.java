package levelgen;

import java.util.*;

public class CorridorGenerator {
    List<RoomHelper> rooms;
    LevelGenerator levelGen;

    public CorridorGenerator(LevelGenerator levelGen) {
        this.levelGen = levelGen;
    }

    public void generate(List<RoomHelper> rooms) {
        this.rooms = rooms;

        List<Edge> mst = generateMST();

        generateCorridors(mst);
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

    private void generateCorridors(List<Edge> mst) {
        for (Edge edge : mst) {
            RoomHelper roomSrc = edge.roomSrc;
            RoomHelper roomDst = edge.roomDst;

            List<int[]> path = findShortestPath(roomSrc.getCenter(), roomDst.getCenter());

            for (int[] point : path) {
                levelGen.map[point[0]][point[1]] = LevelGenerator.TileType.PATH.getValue();
            }
         }
    }

    private List<int[]> findShortestPath(int[] start, int[] end) {
        int width = levelGen.width;
        int height = levelGen.height;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.dist));
        int[][] dist = new int[height][width];
        int[][] prev = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        dist[start[0]][start[1]] = 0;
        pq.offer(new Node(start[0], start[1], 0));

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int x = current.x;
            int y = current.y;

            if (x == end[0] && y == end[1]) {
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx >= 0 && nx < height && ny >= 0 && ny < width &&
                        levelGen.map[nx][ny] != LevelGenerator.TileType.DIRT.getValue()) {
                    int alt = dist[x][y] + 1;
                    if (alt < dist[nx][ny]) {
                        dist[nx][ny] = alt;
                        prev[nx][ny] = i;
                        pq.offer(new Node(nx, ny, dist[nx][ny]));
                    }
                }
            }
        }

        List<int[]> path = new ArrayList<>();
        int x = end[0];
        int y = end[1];

        while (x != start[0] || y != start[1]) {
            path.add(new int[]{x, y});
            int dir = prev[x][y];
            x -= dx[dir];
            y -= dy[dir];
        }

        path.add(start);
        Collections.reverse(path);

        return path;
    }
}
