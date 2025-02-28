package object;

import manager.SheetManager;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Stairs extends SuperObject {

    SheetManager sheetManager;

    public OBJ_Stairs() {
        sheetManager = new SheetManager();
        name = "NewStairsUp";
        try {
            sheetManager.sheet = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/tiles/TX Stairs.png"))));
            image = sheetManager.grabImage(10, 10, 66, 84);
            System.out.println("Im over here");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}