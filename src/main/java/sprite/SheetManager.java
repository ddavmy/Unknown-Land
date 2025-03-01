package sprite;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
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
            if (root.isEmpty()) {
                System.out.println("Data not found");
                return null;
            }

            JsonNode entityRoot = null;

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
            if (directionNode.isEmpty()) {
                System.out.println("Direction not found");
            }

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public BufferedImage grabImage(BufferedImage sheet, int x, int y, int w, int h) {
        return sheet.getSubimage(x, y, w, h);
    }
}
