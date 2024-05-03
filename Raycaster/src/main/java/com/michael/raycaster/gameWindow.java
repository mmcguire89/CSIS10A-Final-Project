package com.michael.raycaster;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class gameWindow extends Application {

    public static Pane canvas;

    @Override
    public void start(final Stage mainStage) throws Exception {
        canvas = new Pane();
        final Scene scene = new Scene(canvas, 800, 600, Color.GRAY);
        mainStage.setTitle("Jolfenstien 3D");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}