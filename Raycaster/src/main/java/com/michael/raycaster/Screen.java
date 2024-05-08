package com.michael.raycaster;

import java.util.ArrayList;

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

    public void update(Camera camera, WritableImage image) {
        PixelWriter writer = image.getPixelWriter();
        for (int x = 0; x < width; x++) {
            double cameraX = 2 * x / (double) width - 1;
            double rayDirX = camera.xDir + camera.xPlane * cameraX;
            double rayDirY = camera.yDir + camera.yPlane * cameraX;
            int mapX = (int) camera.xPos;
            int mapY = (int) camera.yPos;

            double sideDistX;
            double sideDistY;
            double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
            double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
            double perpWallDist;

            int stepX, stepY;
            boolean hit = false;
            int side = 0;

            //see if you can condense this code before Thursday.  

            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (camera.xPos - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
            }

            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (camera.yPos - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
            }

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

                if (map[mapX][mapY] > 0)
                    hit = true;
            }

            if (side == 0)
                perpWallDist = (mapX - camera.xPos + (1 - stepX) / 2) / rayDirX;
            else
                perpWallDist = (mapY - camera.yPos + (1 - stepY) / 2) / rayDirY;

            //We're drawing lines, baby!    
            int lineHeight = (int) (height / perpWallDist);
            int drawStart = Math.max(-lineHeight / 2 + height / 2, 0);
            int drawEnd = Math.min(lineHeight / 2 + height / 2, height - 1);

            // Walls
            Texture texture = textures.get(map[mapX][mapY] - 1);
            double wallX = side == 1 ? camera.xPos + perpWallDist * rayDirX : camera.yPos + perpWallDist * rayDirY;
            wallX -= Math.floor(wallX);

            int texX = (int) (wallX * Texture.SIZE);
            if (side == 0 && rayDirX > 0)
                texX = texture.SIZE - texX - 1;
            if (side == 1 && rayDirY < 0)
                texX = texture.SIZE - texX - 1;

            for (int y = drawStart; y < drawEnd; y++) {
                int texY = (y - drawStart) * Texture.SIZE / lineHeight;
                int color = texture.pixels[texX + texY * Texture.SIZE];
                writer.setColor(x, y, Color.rgb((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff));

            // Ceilings still under construction....

            // Floors still under construction....
            }
        }
    }
}

            
