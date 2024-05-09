package com.michael.raycaster;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

class Screen {
    public int[][] map;
    public int mapWidth, mapHeight, width, height;
    public ArrayList<Texture> textures;

    public Screen(int[][] m, int mapW, int mapH, ArrayList<Texture> tex, int w, int h) {
        map = m;
        mapWidth = mapW;
        mapHeight = mapH;
        textures = tex;
        width = w;
        height = h;
    }

    // see if you can condense/make this more elegant before Thursday.
    public void update(Camera camera, WritableImage image) {
        PixelWriter writer = image.getPixelWriter();
        for (int x = 0; x < width; x++) {
            double cameraX = 2 * x / (double) width - 1;
            double rayDirX = camera.xDir + camera.xPlane * cameraX;
            double rayDirY = camera.yDir + camera.yPlane * cameraX;
            int mapX = (int) camera.xPos;
            int mapY = (int) camera.yPos;

           
            double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
            double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));

            int stepX = rayDirX < 0 ? -1 : 1;
            int stepY = rayDirY < 0 ? -1 : 1;
            double sideDistX = (rayDirX < 0 ? camera.xPos - mapX : mapX + 1.0 - camera.xPos) * deltaDistX;
            double sideDistY = (rayDirY < 0 ? camera.yPos - mapY : mapY + 1.0 - camera.yPos) * deltaDistY;
            boolean hit = false;
            int side = 0;


            while (!hit) {
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                if (map[mapX][mapY] > 0) hit = true;
            }

            double perpWallDist = side == 0 ? 
                (mapX - camera.xPos + (1 - stepX) / 2) / rayDirX : 
                (mapY - camera.yPos + (1 - stepY) / 2) / rayDirY;

            int lineHeight = (int) (height / perpWallDist);
            int drawStart = Math.max(-lineHeight / 2 + height / 2, 0);
            int drawEnd = Math.min(lineHeight / 2 + height / 2, height - 1);

            Texture texture = textures.get(map[mapX][mapY] - 1);
            double wallX = side == 1 ? camera.xPos + perpWallDist * rayDirX : camera.yPos + perpWallDist * rayDirY;
            wallX -= Math.floor(wallX);

            int texX = (int) (wallX * Texture.SIZE);
            if ((side == 0 && rayDirX > 0) || (side == 1 && rayDirY < 0)) {
                texX = Texture.SIZE - texX - 1;
            }

            drawWallSlice(writer, x, drawStart, drawEnd, lineHeight, texture, texX);
            fillCeilingAndFloor(writer, x, drawStart, drawEnd);
        }
    }

    private void drawWallSlice(PixelWriter writer, int x, int drawStart, int drawEnd, int lineHeight, Texture texture, int texX) {
        for (int y = drawStart; y < drawEnd; y++) {
            int texY = (y - drawStart) * Texture.SIZE / lineHeight;
            int color = texture.pixels[texX + texY * Texture.SIZE];
            writer.setColor(x, y, Color.rgb((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff));
        }
    }

    private void fillCeilingAndFloor(PixelWriter writer, int x, int drawStart, int drawEnd) {
        for (int y = 0; y < drawStart; y++) {
            writer.setColor(x, y, Color.SKYBLUE);
        }
        for (int y = drawEnd; y < height; y++) {
            writer.setColor(x, y, Color.DARKSLATEGREY);
        }
    }

    public void renderMiniMap(GraphicsContext gc, Camera camera) {
        int miniMapWidth = 128;
        int miniMapHeight = 128;
        int scale = Math.max(miniMapWidth / mapWidth, miniMapHeight / mapHeight);
        if (scale == 0) scale = 1;

        WritableImage miniMapImage = new WritableImage(miniMapWidth, miniMapHeight);
        PixelWriter miniMapWriter = miniMapImage.getPixelWriter();

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (map[x][y] > 0) {
                    Texture texture = textures.get(map[x][y] - 1);
                    Color tileColor = Color.rgb(
                        (texture.getAverageColor() >> 16) & 0xff,
                        (texture.getAverageColor() >> 8) & 0xff,
                        texture.getAverageColor() & 0xff
                    );
                    drawMiniMapTile(miniMapWriter, x, y, scale, tileColor, miniMapWidth, miniMapHeight);
                }
            }
        }

        drawPlayerOnMiniMap(gc, camera, scale, miniMapImage);
    }

    private void drawMiniMapTile(PixelWriter writer, int x, int y, int scale, Color tileColor, int miniMapWidth, int miniMapHeight) {
        int scaledX = x * scale;
        int scaledY = y * scale;
        for (int i = 0; i < scale; i++) {
            for (int j = 0; j < scale; j++) {
                if (scaledX + i < miniMapWidth && scaledY + j < miniMapHeight) {
                    writer.setColor(scaledX + i, scaledY + j, tileColor);
                }
            }
        }
    }

    private void drawPlayerOnMiniMap(GraphicsContext gc, Camera camera, int scale, WritableImage miniMapImage) {
        int playerX = (int) (camera.xPos * scale);
        int playerY = (int) (camera.yPos * scale);
        double dirX = camera.xDir * 10;
        double dirY = camera.yDir * 10;

        gc.drawImage(miniMapImage, 0, 0);
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeOval(playerX - 2, playerY - 2, 4, 4);
        gc.strokeLine(playerX, playerY, playerX + dirX, playerY + dirY);
    }
}
