package main;

import entity.Entity;

public class CollisionChecker {

    GameLoop gl;

    public CollisionChecker(GameLoop gl) {
        this.gl = gl;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.bounds.x;
        int entityRightWorldX = entity.worldX + entity.bounds.x + entity.bounds.width;
        int entityTopWorldY = entity.worldY + entity.bounds.y;
        int entityBottomWorldY = entity.worldY + entity.bounds.y + entity.bounds.height;

        int entityLeftCol = entityLeftWorldX / gl.tileSize;
        int entityRightCol = entityRightWorldX / gl.tileSize;
        int entityTopRow = entityTopWorldY / gl.tileSize;
        int entityBottomRow = entityBottomWorldY / gl.tileSize;

        int tileNumber1, tileNumber2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gl.tileSize;
                tileNumber1 = gl.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNumber2 = gl.tileManager.mapTileNumber[entityRightCol][entityTopRow];

                if (gl.tileManager.tile[tileNumber1].collision || gl.tileManager.tile[tileNumber2].collision) {
                    entity.collision = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gl.tileSize;
                tileNumber1 = gl.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                tileNumber2 = gl.tileManager.mapTileNumber[entityRightCol][entityBottomRow];

                if (gl.tileManager.tile[tileNumber1].collision || gl.tileManager.tile[tileNumber2].collision) {
                    entity.collision = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gl.tileSize;
                tileNumber1 = gl.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNumber2 = gl.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];

                if (gl.tileManager.tile[tileNumber1].collision || gl.tileManager.tile[tileNumber2].collision) {
                    entity.collision = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gl.tileSize;
                tileNumber1 = gl.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                tileNumber2 = gl.tileManager.mapTileNumber[entityRightCol][entityBottomRow];

                if (gl.tileManager.tile[tileNumber1].collision || gl.tileManager.tile[tileNumber2].collision) {
                    entity.collision = true;
                }
                break;
        }
    }
}
