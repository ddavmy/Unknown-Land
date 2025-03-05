package main;

import java.io.*;
import java.util.*;

public class LevelGenerator {
    private final Map<Integer, Map<String, List<Integer>>> adjacencyMatrix;
    public Map<String, Set<Integer>> worldGrid;
    public int width, height;

    public static void main(String[] args) {
        new LevelGenerator(75,75);
    }

    public LevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        adjacencyMatrix = new HashMap<>();
        worldGrid = new HashMap<>();
        defineRules();
        initializeGrid();
        generateLevel();
        createFileMap();
    }

    // 1.1 Defining adjacency rules
    public void defineRules() {
        adjacencyMatrix.put(0, Map.of(
                "Top", List.of(0, 1),
                "Right", List.of(0, 1),
                "Bottom", List.of(0, 1),
                "Left", List.of(0, 1)
        ));
        adjacencyMatrix.put(1, Map.of(
                "Top", List.of(0, 1, 2),
                "Right", List.of(0, 1, 2),
                "Bottom", List.of(0, 1, 2),
                "Left", List.of(0, 1, 2)
        ));
        adjacencyMatrix.put(2, Map.of(
                "Top", List.of(1, 2),
                "Right", List.of(1, 2),
                "Bottom", List.of(1, 2),
                "Left", List.of(1, 2)
        ));
    }

    // 1.2 Starting with an empty grid
    public void initializeGrid() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                worldGrid.put(i + " " + j, new HashSet<>(adjacencyMatrix.keySet()));
            }
        }
    }

    // 1.3 Collapsing cells
    private String findLeastEntropicCell() {
        String lowestEntropicCell = null;
        int minEntropy = Integer.MAX_VALUE;

        for (Map.Entry<String, Set<Integer>> entry : worldGrid.entrySet()) {
            int entropy = entry.getValue().size();
            if (entropy > 1 && entropy < minEntropy) {
                minEntropy = entropy;
                lowestEntropicCell = entry.getKey();
            }
        }
        return lowestEntropicCell;
    }

    private void collapseCell(String cell) {
        Set<Integer> possibleCells = worldGrid.get(cell);
        if (possibleCells.size() > 1) {
            int choosenTile = new ArrayList<>(possibleCells).get(new Random().nextInt(possibleCells.size()));
            worldGrid.put(cell, new HashSet<>(List.of(choosenTile)));
        }
    }

    // 1.4 Propagating constraints
    public void propagateConstrains(String cell) {
        String[] parts = cell.split(" ");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);

        int choosenTile = worldGrid.get(cell).iterator().next();

        String[] directions = {"Top", "Right", "Bottom", "Left"};
        int[][] offsets = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

        for (int i = 0; i < directions.length; i++) {
            int neighborX = x + offsets[i][0];
            int neighborY = y + offsets[i][1];
            String neighborCell = neighborX + " " + neighborY;

            if (worldGrid.containsKey(neighborCell)) {
                Set<Integer> allowedNeighbors = new HashSet<>(adjacencyMatrix.get(choosenTile).get(directions[i]));
                worldGrid.get(neighborCell).retainAll(allowedNeighbors);
            }
        }
    }

    // 1.5 Repeating until the grid is filled
    public void generateLevel() {
        while (true) {
            String cell = findLeastEntropicCell();
            if (cell == null) break;

            if (worldGrid.get(cell).isEmpty()) {
                System.out.println(cell + " is empty");
                break;
            }
            collapseCell(cell);
            propagateConstrains(cell);
        }
    }

    public void createFileMap() {
        System.out.println("Generating map file...");

        File directory = new File("src/main/resources/maps/");
        File file = new File(directory, "world02.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    writer.write(worldGrid.get(i + " " + j).iterator().next() + " ");
                }
                writer.newLine();
            }
            System.out.println("Map file saved: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
