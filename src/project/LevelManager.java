package project;

import org.newdawn.slick.Input;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public void saveState()
    {
        // first, make a deep copy of the latest gameState (curGameState)
        // credit to https://alvinalexander.com/java/java-deep-clone-example-source-code
        // for showing how to deep copy with serialization
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(curGameState);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            GameState nextGameState = (GameState) ois.readObject();
            // now that we have the new gameState, assign it as our latest one
            // by pushing it onto the end of our gameStates list. updating
            // curGameState & movesMade is all handled in the update method
            gameStates.addLast(nextGameState);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // pops the last item off gameStates, so curGameState will be reverted
    // to the point right before the last save was made (if there are saves
    // available to undo)
    private void undoState()
    {
        if (movesMade > 0 && gameStates.size() > 0) gameStates.removeLast();
    }

    // takes a copy of the initial state, remove everything from gameStates and
    // reassign only the initial state, reverting the state back to how it was
    // when the level was initially loaded
    private void restartLevel()
    {
        GameState initialState = gameStates.getFirst();
//        gameStates.clear();
        gameStates.add(initialState);
    }

    public boolean isBlocked (TileCoord pos)
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
    }

    public void update(Input input)
    {
        // handle user wanting to undo or restart the level
        if (input.isKeyPressed(Input.KEY_Z)) undoState();
        if (input.isKeyPressed(Input.KEY_R)) restartLevel();
        // set curGameState to the most recent version & movesMade,
        // update current SmartSprites
        curGameState = gameStates.getLast();
        movesMade = gameStates.size()-1;
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

