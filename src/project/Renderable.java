package project;

import org.newdawn.slick.Image;

public interface Renderable {
     default void drawToScreen(Image img,
                               TileCoord pos,
                               TileCoord worldDimensions)
     {
        // convert tile position to pixel position and draw to screen, centered
        int xPixel =
                // make all the tiles render in the top left of the screen
                (pos.getX() * App.TILE_SIZE) +
                // push the tiles half a screen width to the right
                (App.SCREEN_WIDTH / 2) -
                // push the tiles half the world dimensions in the other
                // direction
                (worldDimensions.getX() * App.TILE_SIZE / 2) -
                // if the sprite has a weird size (looking at you, TNT) make it
                // move the ratio of its size relative to the grid fwd or back
                // so it is aligned
                (img.getWidth() / App.TILE_SIZE * App.TILE_SIZE);

        // do the same as xPixel, but on the y axis
        int yPixel = (pos.getY() * App.TILE_SIZE) +
                     (App.SCREEN_HEIGHT / 2) -
                     (worldDimensions.getY() * App.TILE_SIZE / 2) -
                     (img.getHeight() / App.TILE_SIZE * App.TILE_SIZE);

        // finally draw the image to the screen!
        img.draw(xPixel, yPixel);
    }
}
