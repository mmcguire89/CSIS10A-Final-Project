package com.michael.raycaster;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game extends Application {
    public int mapWidth = 10;
    public int mapHeight = 10;
    // Make the map bigger and a little more complex once you have your movement and performance issues figured out...
    public static int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 1, 1, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private Camera camera;
    private Screen screen;
    private WritableImage writableImage;

    @Override
    public void start(Stage primaryStage) {
        // Create the canvas at 640x480 pixels.  
        Canvas canvas = new Canvas(640, 480);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        this.writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());

        // Textures array list
        ArrayList<Texture> textures = new ArrayList<>();
        textures.add(new Texture("wood.png"));
        textures.add(new Texture("brick.png"));
        textures.add(new Texture("bluestone.png"));
        textures.add(new Texture("stone.png"));

        // Camera starting pos
        camera = new Camera(4.5, 4.5, 1, 0, 0, -0.66);
        screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);

        // Make and name the window
        primaryStage.setTitle("Jolfenstein 4D");
        Scene scene = new Scene(new StackPane(canvas));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Register keyboard event handlers on key press and key release
        scene.setOnKeyPressed(camera.keyPressedHandler);
        scene.setOnKeyReleased(camera.keyReleasedHandler);  
        
        //Might need to add an FPS limiter here; still getting wonky performance
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render(gc);
            }
        };
        timer.start();
    }
//getting some complaints about update... investigate more....
    public void update() {
        camera.update(map);
    }

    public void render(GraphicsContext gc) {
        screen.update(camera, writableImage);
        gc.drawImage(writableImage, 0, 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}