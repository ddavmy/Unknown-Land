package main;

import object.*;

public class AssetSetter {
    GameLoop gl;

    public AssetSetter(GameLoop gl) {
        this.gl = gl;
    }

    public void setObject() {
        gl.object[0] = new OBJ_Key();
        gl.object[0].worldX = 116 * gl.tileSize;
        gl.object[0].worldY = 46 * gl.tileSize;

        gl.object[1] = new OBJ_Book();
        gl.object[1].worldX = 124 * gl.tileSize;
        gl.object[1].worldY = 53 * gl.tileSize;

        gl.object[2] = new OBJ_Stone();
        gl.object[2].worldX = 123 * gl.tileSize;
        gl.object[2].worldY = 42 * gl.tileSize;
        gl.object[2].collision = true;

        gl.object[3] = new OBJ_Chest();
        gl.object[3].worldX = 128 * gl.tileSize;
        gl.object[3].worldY = 49 * gl.tileSize;
        gl.object[3].collision = true;
    }
}
