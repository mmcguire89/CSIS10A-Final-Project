package com.michael.raycaster;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

class Texture {
    public static final int SIZE = 64;
    public int[] pixels;

    public Texture(String fileName) {
        loadTexture(fileName);
    }

    private void loadTexture(String fileName) {  // texture loading method
        try {
            String path = getClass().getResource("/" + fileName).toExternalForm(); //pulls the external resource
            Image image = new Image(path); //path is responsible for image location.
            PixelReader reader = image.getPixelReader(); //feeds image to pixel reader for rasterization
            pixels = new int[SIZE * SIZE]; //adds the texture to the pixels array 
            for (int y = 0; y < SIZE; y++) {
                for (int x = 0; x < SIZE; x++) {
                    pixels[x + y * SIZE] = reader.getArgb(x, y);
                }
            }
        } catch (Exception e) { //it wouldn't compile without this... couldn't get 'finally' to work right
        }
    }
}
