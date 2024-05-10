package com.michael.raycaster;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Camera {
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back;
    public final double MOVE_SPEED = .08; //forward/backwards movement speed.
    public final double ROTATION_SPEED = .045; //rotation speed.

    public Camera(double x, double y, double xd, double yd, double xp, double yp) {
        //Positions of camera in map, direction vector of camera, plane vector for FOV.
        xPos = x;
        yPos = y;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;
    }

    // Key press handler
    public EventHandler<KeyEvent> keyPressedHandler = event -> {
        switch (event.getCode()) {
            case A -> left = true;
            case D -> right = true;
            case W -> forward = true;
            case S -> back = true;
            default -> {
            }
        }

    };
    // Key release handler
    public EventHandler<KeyEvent> keyReleasedHandler = event -> {
        switch (event.getCode()) {
            case A -> left = false;
            case D -> right = false;
            case W -> forward = false;
            case S -> back = false;
            default -> {
            }
        }
    };

    // Move the camera forward or backward
    private void move(int[][] map, double dx, double dy) {
        if (map[(int) (xPos + dx)][(int) yPos] == 0) {
            xPos += dx;
        }
        if (map[(int) xPos][(int) (yPos + dy)] == 0) {
            yPos += dy;
        }
    }

    // Rotate the camera left or right in radians
    private void rotate(double angle) {
        double oldDirX = xDir;
        xDir = xDir * Math.cos(angle) - yDir * Math.sin(angle);
        yDir = oldDirX * Math.sin(angle) + yDir * Math.cos(angle);

        double oldPlaneX = xPlane;
        xPlane = xPlane * Math.cos(angle) - yPlane * Math.sin(angle);
        yPlane = oldPlaneX * Math.sin(angle) + yPlane * Math.cos(angle);
    }
    //Condensed movement logic.
    public void update(int[][] map) {
        if (forward) {
            move(map, xDir * MOVE_SPEED, yDir * MOVE_SPEED);
        }
        if (back) {
            move(map, -xDir * MOVE_SPEED, -yDir * MOVE_SPEED);
        }
        if (right) {
            rotate(-ROTATION_SPEED);
        }
        if (left) {
            rotate(ROTATION_SPEED);
        }
    }
}
