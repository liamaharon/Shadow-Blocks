package project;

import org.newdawn.slick.Input;

import java.util.ArrayList;
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
    private List<GameState> gameStates;
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

        gameStates = new ArrayList<>();
        gameStates.add(initialGameState);
        // need to set these here because the initial render call is made
        // before curGameState is initially set in update()
        curGameState = initialGameState;
        movesMade = 0;
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
        // set curGameState to the most recent version & movesMade,
        // update current SmartSprites
        curGameState = gameStates.get(gameStates.size()-1);
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

