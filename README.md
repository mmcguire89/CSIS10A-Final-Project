# CSIS10A-Final-Project-3DRayCaster
Repo for CSIS10A Final Project

## This project will attempt to build a 3D Raycastyer in Java using JavaFX to draw graphics.

### Step 1: Build a top-down representation of a level using a grid design.
  - Create an array to hold the values of walls vs. empty space.
  - Add these to your scene in Java FX.
  - Verify level displays correctly in drawn window.
  - Add an actor to the scene and define viewing direction.
  

### Step 2: Figure out movement, rotation, and rays.
  - Create movement controls.
  - Use trig to create movement rotation - convert 0 - 360 to radians.
  - Cast a ray from the actor and use trigonometry to figure out x,y offset between grid units.
  - Determine how to store where rays intersect with horizontal and vertical lines.
  - Add additional rays to simulate FOV.

### Step 3: Start converting to 3D
  - Convert/draw walls in 3D using JavaFX - hopefully the FPS isn't horrible.
  - Verify FOV is set to the middle of the screen, fix if not.
  - Fix fisheye effect on outer rays.
  - Add colors or textures to walls, floors, and ceilings
