package sprite;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SheetManager {

    public SpriteData[] SpriteLoader(String spriteDataFile, String name, String state, String direction) {
        ObjectMapper mapper = new ObjectMapper();

        InputStream stream = getClass().getResourceAsStream(spriteDataFile);
        if (stream == null) {
            System.out.println("File not found: " + spriteDataFile);
            return null;
        }

        try {
            JsonNode root = mapper.readTree(stream);
            JsonNode entityRoot = null;

            if (root.isEmpty()) {
                System.out.println("Data not found");
                return null;
            }

            for (JsonNode node : root) {
                if (node.has(name)) {
                    entityRoot = node.get(name);
                    break;
                }
            }

            if (entityRoot == null) {
                System.out.println("Entity " + name + "not found");
                return null;
            }

            JsonNode stateNode = entityRoot.get(state);
            if (stateNode.isEmpty()) {
                System.out.println("State not found");
                return null;
            }

            JsonNode directionNode = stateNode.get(direction);
            if (directionNode == null || directionNode.isEmpty()) {
                System.out.println("Direction " + direction + " not found");

                if (direction.equals("right")) {
                    JsonNode leftNode = stateNode.get("left");
                    if (leftNode != null && !leftNode.isEmpty()) {
                        System.out.println("Flipping left to create right direction");
                        SpriteData[] leftSprites = extractSpriteData(leftNode);
                        return flippedSprite(leftSprites);
                    }
                }
            }

            assert directionNode != null;
            return getSpriteData(directionNode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private SpriteData[] extractSpriteData(JsonNode directionNode) {
        return getSpriteData(directionNode);
    }

    private SpriteData[] getSpriteData(JsonNode directionNode) {
        SpriteData[] spriteData = new SpriteData[directionNode.size()];
        for (int i = 0; i < directionNode.size(); i++) {
            JsonNode frame = directionNode.get(i);
            spriteData[i] = new SpriteData(
                    frame.get("x").asInt(),
                    frame.get("y").asInt(),
                    frame.get("w").asInt(),
                    frame.get("h").asInt()
            );
        }
        return spriteData;
    }

    public SpriteData[] flippedSprite(SpriteData[] leftSprites) {
        SpriteData[] flippedSprites = new SpriteData[leftSprites.length];

        for (int i = 0; i < leftSprites.length; i++) {
            SpriteData original = leftSprites[i];
            flippedSprites[i] = new SpriteData(original.x, original.y, original.w, original.h, true);
        }

        return flippedSprites;
    }

    public BufferedImage grabImage(BufferedImage sheet, int x, int y, int w, int h) {
        return sheet.getSubimage(x, y, w, h);
    }
}
