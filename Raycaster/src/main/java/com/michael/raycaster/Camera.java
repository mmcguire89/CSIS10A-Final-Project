package com.michael.raycaster;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Camera {
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back;
    public final double MOVE_SPEED = .08; //
    public final double ROTATION_SPEED = .045;  //

    public Camera(double x, double y, double xd, double yd, double xp, double yp) {
        xPos = x;
        yPos = y;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;
    }
    // holy shit this condensed logic works so much better.
    public EventHandler<KeyEvent> keyPressedHandler = event -> {
        switch (event.getCode()) {
            case A -> left = true;
            case D -> right = true;
            case W -> forward = true;
            case S -> back = true;
            default -> throw new IllegalArgumentException("Unexpected value: " + event.getCode());
        }
    };

    public EventHandler<KeyEvent> keyReleasedHandler = event -> {
        switch (event.getCode()) {
            case A -> left = false;
            case D -> right = false;
            case W -> forward = false;
            case S -> back = false;
            default -> throw new IllegalArgumentException("Unexpected value: " + event.getCode());
        }
    };
// try to trim this code if possible.  Can you approximate constants for your trig math?
    public void update(int[][] map) {
        if (forward) {
            if (map[(int) (xPos + xDir * MOVE_SPEED)][(int) yPos] == 0) {
                xPos += xDir * MOVE_SPEED;
            }
            if (map[(int) xPos][(int) (yPos + yDir * MOVE_SPEED)] == 0)
                yPos += yDir * MOVE_SPEED;
        }
        if (back) {
            if (map[(int) (xPos - xDir * MOVE_SPEED)][(int) yPos] == 0)
                xPos -= xDir * MOVE_SPEED;
            if (map[(int) xPos][(int) (yPos - yDir * MOVE_SPEED)] == 0)
                yPos -= yDir * MOVE_SPEED;
        }
        if (right) {
            double oldxDir = xDir;
            xDir = xDir * Math.cos(-ROTATION_SPEED) - yDir * Math.sin(-ROTATION_SPEED);
            yDir = oldxDir * Math.sin(-ROTATION_SPEED) + yDir * Math.cos(-ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane = xPlane * Math.cos(-ROTATION_SPEED) - yPlane * Math.sin(-ROTATION_SPEED);
            yPlane = oldxPlane * Math.sin(-ROTATION_SPEED) + yPlane * Math.cos(-ROTATION_SPEED);
        }
        if (left) {
            double oldxDir = xDir;
            xDir = xDir * Math.cos(ROTATION_SPEED) - yDir * Math.sin(ROTATION_SPEED);
            yDir = oldxDir * Math.sin(ROTATION_SPEED) + yDir * Math.cos(ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane = xPlane * Math.cos(ROTATION_SPEED) - yPlane * Math.sin(ROTATION_SPEED);
            yPlane = oldxPlane * Math.sin(ROTATION_SPEED) + yPlane * Math.cos(ROTATION_SPEED);
        }
    }
}
