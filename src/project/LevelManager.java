package project;

import org.newdawn.slick.Input;

import java.util.LinkedList;
import java.util.List;

public class LevelManager
{
    private TileCoord levelDimensions;
    private int movesMade;
    private List<List<Boolean>> permBlockedTiles;
    private List<List<Boolean>> targetTiles;
    private int nTargetTiles;
    private List<RegularSprite> regularSprites;
    private TileCoord switchCoord;
    private TileCoord doorCoord;
    private LinkedList<GameState> gameStates;
    private GameState curGameState;

    public LevelManager(TileCoord levelDimensions,
                        List<List<Boolean>> permBlockedTiles,
                        List<List<Boolean>> targetTiles,
                        int nTargetTiles,
                        List<RegularSprite> regularSprites,
                        TileCoord switchCoord,
                        TileCoord doorCoord,
                        GameState initialGameState)
    {
        this.levelDimensions = levelDimensions;
        this.permBlockedTiles = permBlockedTiles;
        this.targetTiles = targetTiles;
        this.nTargetTiles = nTargetTiles;
        this.regularSprites = regularSprites;
        this.switchCoord = switchCoord;
        this.doorCoord = doorCoord;

        gameStates = new LinkedList<>();
        gameStates.addLast(initialGameState);
        // need to set these here because the initial render call is made
        // before curGameState is initially set in update()
        curGameState = initialGameState;
        movesMade = 0;
    }

    // take a copy of the current GameState and put it in the second last
    // position in the linkedlist behind curGameState, effectively saving the
    // game. also increment moves made.
    public void saveState()
    {
        int indexToSave = gameStates.size()-1;
        gameStates.add(indexToSave, DeepCopier.deepCopyGameState(curGameState));
        movesMade++;
    }

    // pops the last item off gameStates, sets the new tail to be the new
    // curGameState, decrement movesMade
    private void undoState()
    {
        if (movesMade > 0 && gameStates.size() > 0)
        {
            gameStates.removeLast();
            curGameState = gameStates.getLast();
            movesMade--;
        }
    }

    // set the curGameState to the initial GameState which is the first element
    // in the linkedList, clear the list then read only the curGameState
    // (initial state) effectively resetting the level back to it's initial state
    private void restartLevel()
    {
        curGameState = gameStates.getFirst();
        gameStates.clear();
        gameStates.add(curGameState);
        movesMade = 0;
    }

    public boolean isBlocked (TileCoord pos, Direction dir)
    {
        return  // check if blocked by permanent blockage
                permBlockedTiles
                        .get(pos.getX())
                        .get(pos.getY()) ||
                // check if blocked by cracked wall
                curGameState
                        .getCrackedWalls()
                        .get(pos.getX())
                        .get(pos.getY()) != null ||
                // check if blocked by a closed door
                doorCoord != null &&
                doorCoord.equals(pos) &&
                !curGameState.getSwitchIsCovered();
                // check if blocked by a block that is itself blocked
                // making it unpushable
                getPushableTile(pos) != null &&
                getPushableTile(nextPos) != null;
    }

    // returns a reference to a pushable tile in a coord. if one does not exist
    // null is returned
    public Block getPushableTile(TileCoord pos)
    {
        return curGameState
                .getBlocks()
                .get(pos.getX())
                .get(pos.getY());
    }

    public void update(Input input)
    {
        // handle user wanting to undo or restart the level
        if (input.isKeyPressed(Input.KEY_Z)) undoState();
        if (input.isKeyPressed(Input.KEY_R)) restartLevel();

        // update our smartSprites
        for (SmartSprite smartSprite : curGameState.getSmartSprites())
        {
            smartSprite.update(input, this);
        }
    }

    public void render()
    {
        // redraw RegularSprites that never update over the course of a level
        for (RegularSprite regSprite : regularSprites)
        {
          regSprite.render(levelDimensions);
        }
        // redraw SmartSprites in the current GameState
        for (SmartSprite smartSprite : curGameState.getSmartSprites())
        {
            smartSprite.render(levelDimensions);
        }
    }
}
