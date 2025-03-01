package main;

import object.*;

public class AssetSetter {
    GameLoop gl;

    public AssetSetter(GameLoop gl) {
        this.gl = gl;
    }

    public void setObject() {
        gl.object[0] = new OBJ_Key();
        gl.object[0].worldX = 21 * gl.tileSize;
        gl.object[0].worldY = 36 * gl.tileSize;

        gl.object[1] = new OBJ_Book();
        gl.object[1].worldX = 10 * gl.tileSize;
        gl.object[1].worldY = 23 * gl.tileSize;

        gl.object[2] = new OBJ_Stone();
        gl.object[2].worldX = 32 * gl.tileSize;
        gl.object[2].worldY = 27 * gl.tileSize;
        gl.object[2].collision = true;

        gl.object[3] = new OBJ_Chest();
        gl.object[3].worldX = 43 * gl.tileSize;
        gl.object[3].worldY = 36 * gl.tileSize;
        gl.object[3].collision = true;
    }
}
