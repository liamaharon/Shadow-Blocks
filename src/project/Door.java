package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Door extends SmartSprite {
    private boolean isOpen;

    public Door(TileCoord pos) throws SlickException {
        super("res/door.png", pos);
        isOpen = false;
    }

    // state of door being open or not is tied to the switch being covered
    // or not. although the isOpen attribute is unnecessary, it keeps the door
    // class modular and adaptive for the case when we might want its open
    // state to be tied to something else
    @Override
    public void update(Input input, LevelManager levelManager) throws SlickException
    {
        super.update(input, levelManager);
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
