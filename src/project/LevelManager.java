package project;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.LinkedList;
import java.util.List;

public class LevelManager
{
    private TileCoord levelDimensions;
    private int movesMade = 0;
    private List<List<Boolean>> permBlockedTiles;
    private List<List<Boolean>> targetTiles;
    private int nTargetTiles;
    private int nTargetTilesCovered = 0;
    private boolean levelComplete = false;
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

        // setup the initial gamestate!
        gameStates = new LinkedList<>();
        gameStates.addLast(initialGameState);
        curGameState = initialGameState;
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

    // returns if a tile is permanently blocked by a wall
    public boolean tileIsBlockedByWall(TileCoord pos)
    {
        return permBlockedTiles
                .get(pos.getX())
                .get(pos.getY());
    }

    // returns a reference to a cracked wall in a coord. if one does not exist
    // null is returned.
    public CrackedWall getCrackedWall(TileCoord pos)
    {
        return curGameState
                .getCrackedWalls()
                .get(pos.getX())
                .get(pos.getY());
    }

    // return if a tile is blocked by a door (the door must be closed, obviously)
    public boolean tileIsBlockedByDoor(TileCoord pos)
    {
        return  doorCoord != null &&
                doorCoord.equals(pos) &&
                !curGameState.getSwitchIsCovered();
    }

    // returns a reference to a pushable block in a coord. if one does not exist
    // null is returned
    public Block getPushableTile(TileCoord pos)
    {
        return curGameState
                .getBlocks()
                .get(pos.getX())
                .get(pos.getY());
    }

    public boolean tileIsTarget(TileCoord pos)
    {
        return targetTiles
                .get(pos.getX())
                .get(pos.getY()) != null;
    }

    // move a block from one place to another in the current GameStates
    // blocks 2D lookup array. also update the nTilesCovered if appropriate.
    // if cur is null it means the block is being removed from the game
    public void updateCurState2DBlocksList(TileCoord prev, TileCoord cur)
    {
        // take a note of the block we're moving
        Block blockToMove = curGameState
                .getBlocks()
                .get(prev.getX())
                .get(prev.getY());

        // nullify its previous position
        curGameState
                .getBlocks()
                .get(prev.getX())
                .set(prev.getY(), null);

        // set the new position inside of the 2D array
        curGameState
                .getBlocks()
                .get(cur.getX())
                .set(cur.getY(), blockToMove);

        // handle if the block is moving on or off a target tile
        boolean targetTileAtPrev = targetTiles.get(prev.getX()).get(prev.getY());
        boolean targetTileAtCur = targetTiles.get(cur.getX()).get(cur.getY());
        if (targetTileAtPrev && !targetTileAtCur) nTargetTilesCovered--;
        if (targetTileAtCur && !targetTileAtPrev) nTargetTilesCovered++;
    }

    // removes a smart sprite from the current game state
    public void removeSpriteFromCurGameState(SmartSprite smartSprite)
    {
        // remove from list of every smartSprite
        curGameState.getSmartSprites().remove(smartSprite);

        // if it exists in a 2D array remove it from that too
        TileCoord pos = smartSprite.getPos();
        switch(smartSprite.getClass().getSimpleName())
        {
            case("CrackedWall"): curGameState
                    .getCrackedWalls()
                    .get(pos.getX())
                    .set(pos.getY(), null); break;
            case("Tnt"): curGameState
                    .getBlocks()
                    .get(pos.getX())
                    .set(pos.getY(), null); break;
        }
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

        // check if user has completed the level
        if (nTargetTiles == nTargetTilesCovered) levelComplete = true;
    }

    public void render(Graphics graphics)
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
        // draw moves made 10px down 10px right from the top left corner
        graphics.drawString("Moves: " + movesMade, 10, 10);
    }
    public boolean getLevelComplete() {
        return levelComplete;
    }
}

