
### `Game` Class

This is the main class that extends `Application` from JavaFX and sets up the game.

- **Fields:**
  - `mapWidth`, `mapHeight`: Sets dimensions of the game map.
  - `map`: An array representing the game map where `0` is an open space and other numbers represent walls with different textures.
  - `camera`: An instance of the `Camera` class representing the player's viewpoint.
  - `screen`: An instance of the `Screen` class used for rendering the game view.
  - `writableImage`: A JavaFX `WritableImage` used to display the rendered game.

- **Methods:**
  - `start`: This method sets up the game window using JavaFX. It creates a canvas, initializes the camera and screen, and starts the game loop using `AnimationTimer`.
    - **Parameters:**
      - `primaryStage`: The main stage (window) provided by JavaFX.
  
  - `update()`: Updates the game state by moving the camera based on current input.

  - `render(GraphicsContext gc)`: Renders the game frame by drawing the current view to the canvas and then the mini-map.
    - **Parameters:**
      - `gc`: The `GraphicsContext` of the canvas, which allows drawing operations.

  - `main(String[] args)`: The entry point of the application which launches the JavaFX application.

### `Camera` Class

Represents the player's viewpoint and handles movement and rotation based on user input.

- **Fields:**
  - `xPos`, `yPos`: The current position of the camera in the map.
  - `xDir`, `yDir`: The direction vector of the camera, indicating where it's looking.
  - `xPlane`, `yPlane`: The camera plane vector perpendicular to the direction vector; determines the field of view.
  - `left`, `right`, `forward`, `back`: Boolean flags indicating which movement is currently active.
  - `MOVE_SPEED`, `ROTATION_SPEED`: Constants representing the movement speed and rotation speed of the camera.

- **Methods:**
  - `Camera(double x, double y, double xd, double yd, double xp, double yp)`: Constructor to initialize the camera's position and direction.
    - **Parameters:**
      - `x`, `y`: Initial position.
      - `xd`, `yd`: Initial direction.
      - `xp`, `yp`: Initial camera plane.

  - `keyPressedHandler`: Handles key press events to start movement or rotation.

  - `keyReleasedHandler`: Handles key release events to stop movement or rotation.

  - `move(int[][] map, double dx, double dy)`: Moves the camera if the next position is in an open space (`0`).
    - **Parameters:**
      - `map`: The game map array.
      - `dx`, `dy`: The change in position in the x and y directions.

  - `rotate(double angle)`: Rotates the camera by updating its direction and camera plane vectors.
    - **Parameters:**
      - `angle`: The angle to rotate (in radians).

  - `update(int[][] map)`: Updates the camera's position and rotation based on which keys are pressed.
    - **Parameters:**
      - `map`: The game map array.

### `Screen` Class

Responsible for rendering the game's 3D view and the mini-map.

- **Fields:**
  - `map`: The game map.
  - `mapWidth`, `mapHeight`: Dimensions of the game map.
  - `width`, `height`: The dimensions of the screen (rendering area).
  - `textures`: An `ArrayList` of `Texture` objects used for the walls.

- **Methods:**
  - `Screen(int[][] m, int mapW, int mapH, ArrayList<Texture> tex, int w, int h)`: Constructor to initialize the screen with the map, its dimensions, textures, and the screen size.
    - **Parameters:**
      - `m`: Game map.
      - `mapW`, `mapH`: Dimensions of the map.
      - `tex`: Textures for the walls.
      - `w`, `h`: Dimensions of the screen.

  - `update(Camera camera, WritableImage image)`: Renders the view based on the camera's position and direction.
    - **Parameters:**
      - `camera`: The camera (player's viewpoint).
      - `image`: The writable image used to display the rendered view.

  - `drawWallSlice(PixelWriter writer, int x, int drawStart, int drawEnd, int lineHeight, Texture texture, int texX)`: Draws a vertical slice of a wall.
    - **Parameters:**
      - `writer`: Pixel writer for drawing.
      - `x`: The x-coordinate on the screen to draw the slice.
      - `drawStart`, `drawEnd`: The start and end y-coordinates on the screen.
      - `lineHeight`: The height of the line to draw.
      - `texture`: The wall texture.
      - `texX`: The x-coordinate in the texture.

  - `fillCeilingAndFloor(PixelWriter writer, int x, int drawStart, int drawEnd)`: Fills the ceiling and floor with colors.
    - **Parameters:**
      - `writer`: Pixel writer for drawing.
      - `x`: The x-coordinate on the screen.
      - `drawStart`, `drawEnd`: The start and end y-coordinates for the ceiling and floor.

  - `renderMiniMap(GraphicsContext gc, Camera camera)`: Renders the mini-map.
    - **Parameters:**
      - `gc`: Graphics context for drawing.
      - `camera`: The camera (player's viewpoint).

  - `drawMiniMapTile(PixelWriter writer, int x, int y, int scale, Color tileColor, int miniMapWidth, int miniMapHeight)`: Draws a tile on the mini-map.
    - **Parameters:**
      - `writer`: Pixel writer for the mini-map.
      - `x`, `y`: Coordinates on the mini-map.
      - `scale`: Scale factor for the mini-map tiles.
      - `tileColor`: Color of the tile.
      - `miniMapWidth`, `miniMapHeight`: Dimensions of the mini-map.

  - `drawPlayerOnMiniMap(GraphicsContext gc, Camera camera, int scale, WritableImage miniMapImage)`: Draws the player on the mini-map.
    - **Parameters:**
      - `gc`: Graphics context for drawing.
      - `camera`: The camera (player's viewpoint).
      - `scale`: Scale factor for the mini-map.
      - `miniMapImage`: The image of the mini-map.

### `Texture` Class

Loads and stores textures for the game walls.

- **Fields:**
  - `SIZE`: The size of the texture (64x64 pixels).
  - `pixels`: An array of pixel data for the texture.

- **Methods:**
  - `Texture`: Constructor to load a texture from a file.
    - **Parameters:**
      - `fileName`: The filename of the texture image.

  - `loadTexture(String fileName)`: Loads the texture from the specified file into the `pixels` array.
    - **Parameters:**
      - `fileName`: The filename of the texture image.

  - `getAverageColor()`: Computes the average color of the texture for use in the mini-map.
    - **Returns:**
      - The average color of the texture.



Main references:
https://www.instructables.com/Making-a-Basic-3D-Engine-in-Java/
https://lodev.org/cgtutor/raycasting.html
https://www.youtube.com/watch?v=gYRrGTC7GtA
