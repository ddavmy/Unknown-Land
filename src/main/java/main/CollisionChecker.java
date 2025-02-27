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

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gl.object.length; i++) {
            if (gl.object[i] != null) {
                entity.bounds.x = entity.worldX + entity.bounds.x;
                entity.bounds.y = entity.worldY + entity.bounds.y;
                gl.object[i].bounds.x = gl.object[i].worldX + gl.object[i].bounds.x;
                gl.object[i].bounds.y = gl.object[i].worldY + gl.object[i].bounds.y;

                switch (entity.direction) {
                    case "up":
                        entity.bounds.y -= entity.speed;
                        if (entity.bounds.intersects(gl.object[i].bounds)) {
                            if (gl.object[i].collision) {
                                entity.collision = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.bounds.y += entity.speed;
                        if (entity.bounds.intersects(gl.object[i].bounds)) {
                            if (gl.object[i].collision) {
                                entity.collision = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.bounds.x -= entity.speed;
                        if (entity.bounds.intersects(gl.object[i].bounds)) {
                            if (gl.object[i].collision) {
                                entity.collision = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.bounds.x += entity.speed;
                        if (entity.bounds.intersects(gl.object[i].bounds)) {
                            if (gl.object[i].collision) {
                                entity.collision = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.bounds.x = entity.boundsDefaultX;
                entity.bounds.y = entity.boundsDefaultY;
                gl.object[i].bounds.x = gl.object[i].boundsDefaultX;
                gl.object[i].bounds.y = gl.object[i].boundsDefaultY;
            }
        }
        return index;
    }
}
