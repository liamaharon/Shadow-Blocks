package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *The door tile is controlled by the switch tile. When it is displayed, it is
 * “locked”, and units cannot move through it. When the door tile is unlocked
 * by the switch tile, it should not be visible, and units can move freely
 * through the tile.
 * Acknowledgement:
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class Door extends SmartSprite {
    private boolean isOpen;

    /**
     * Initialise the Door with it's img source location, and it's open status
     * set to false
     * @param pos The initial location of the door
     */
    public Door(TileCoord pos) throws SlickException {
        super("res/door.png", pos);
        isOpen = false;
    }

    /**
     * The state of door being open or not is tied to the switch being covered
     * or not. Check if the switch is covered and update isOpen every update.
     * Although the isOpen attribute is unnecessary, it keeps the door
     * class modular and adaptive for the case when we might want its open
     * state to be tied to something else
     * @param levelManager The LevelManager managing the Door's level
     * @param input        Represents any input made this update
     * @param delta        Represents the time in ms since the last update was
     *                     made
     */
    @Override
    public void update(LevelManager levelManager, Input input, int delta) throws SlickException
    {
        super.update(levelManager, input, delta);
        // switch is covered and door is closed - open the door!
        if (levelManager.getCurGameState().getSwitchIsCovered() && !isOpen)
        {
            isOpen = true;
        }
        // switch is uncovered and door is open - close the door!
        else if (!levelManager.getCurGameState().getSwitchIsCovered() && isOpen)
        {
            isOpen = false;
        }
    }

    // the door only renders when it is not in its open state ie is closed.
    @Override
    public void render(TileCoord worldDimensions) {
        if (!isOpen) super.render(worldDimensions);
    }
}
