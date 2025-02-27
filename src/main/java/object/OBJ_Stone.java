package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Stone extends SuperObject {

    public OBJ_Stone() {
        name = "Stone";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/stone.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
