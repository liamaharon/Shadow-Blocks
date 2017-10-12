package project;

import org.lwjgl.Sys;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.LinkedList;
import java.util.List;

public class LevelManager
{
    private TileCoord levelDimensions;
    private int movesMade = 0;
    private List<List<Boolean>> permBlockedTiles;
    private List<List<Boolean>> targetTiles;
    private int nTargetTiles;
    private boolean levelComplete = false;
    private List<RegularSprite> regularSprites;
    private TileCoord switchCoord;
    private TileCoord doorCoord;
    private GameState initialGameState;
    private LinkedList<GameState> gameStates;
    private GameState curGameState;
    private Direction playerDirectionThisTick;

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
        this.initialGameState = initialGameState;

        // setup the gameState LinkedList!
        gameStates = new LinkedList<>();
        // keep the initial GameState guaranteed pristine by never directly
        // adding it to gameStates where it could be mutated
        gameStates.add(DeepCopier.deepCopyGameState(initialGameState));
        curGameState = gameStates.getFirst();
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

    // remove everything from gameStates, and re add the initial GameState
    // effectively setting everything up the way it was when the level first
    // was loaded
    public void restartLevel()
    {
        gameStates.clear();
        // keep the initial GameState guaranteed pristine by never directly
        // adding it to gameStates where it could be mutated
        gameStates.add(DeepCopier.deepCopyGameState(initialGameState));
        curGameState = gameStates.getFirst();
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
    public CrackedWall getCrackedWallFromCoord(TileCoord pos)
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

    // returns if a tile is blocked by a pushable block that is itself blocked
    public boolean tileIsBlockedByBlockedBlock(TileCoord pos, Direction dir)
    {
        // try get a block from the provided pos
        Block blockAtPos = getBlockFromCoord(pos);
        // if there's no block in the provided position, return false
        if (blockAtPos == null) return false;

        // get the position that is adjacent to the block, in the direction it's
        // being pushed
        TileCoord adjacentTileCoord = getAdjacentTileCoord(pos, dir);

        // return if the block isn't able to move into the position adjacent to it,
        // in the direction specified
        return !blockAtPos.canMoveTo(adjacentTileCoord, this);
    }

    // returns a reference to a pushable block in a coord. if one does not exist
    // null is returned
    public Block getBlockFromCoord(TileCoord pos)
    {
        return curGameState
                .getBlocks()
                .get(pos.getX())
                .get(pos.getY());
    }

    // returns a TileCoord with position directly adjacent to the TileCoord
    // input, in the direction specified
    public TileCoord getAdjacentTileCoord(TileCoord pos, Direction dir)
    {
        switch(dir)
        {
            case UP: return new TileCoord(pos.getX(), pos.getY()-1);
            case DOWN: return new TileCoord(pos.getX(), pos.getY()+1);
            case LEFT: return new TileCoord(pos.getX()-1, pos.getY());
            case RIGHT: return new TileCoord(pos.getX()+1, pos.getY());
            case NONE: return pos;
        }
        return null;
    }

    // move a block from one place to another in the current GameStates
    // blocks 2D lookup array. also update the nTilesCovered && switchIsCovered
    // if required.
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
        if (targetTileAtPrev && !targetTileAtCur) curGameState.decrementTargetTilesCovered();
        if (targetTileAtCur && !targetTileAtPrev) curGameState.incrementTargetTilesCovered();

        // if a switch exists, handle if the block is moving on or off the switch
        if (switchCoord != null)
        {
            boolean switchAtPrev = switchCoord.equals(prev);
            boolean switchAtCur = switchCoord.equals(cur);
            if (switchAtPrev && !switchAtCur) curGameState.setSwitchIsCovered(false);
            if (switchAtCur && !switchAtPrev) curGameState.setSwitchIsCovered(true);
        }
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

    // creates a new explosion at a given position and adds it to the
    // curGameState smartSprites
    public void createExplosion(TileCoord pos) throws SlickException
    {
        curGameState.getSmartSprites().add(new Explosion(pos));
    }

    // need to watch for player input before we update the sprites, as some
    // sprites actions (rogue) depend on if a player will move before the
    // player itself actually updates
    private void watchForPlayerInput(Input input)
    {
        if (input.isKeyPressed(Input.KEY_UP))
        {
            playerDirectionThisTick = Direction.UP;
            saveState();
        }
        else if (input.isKeyPressed(Input.KEY_DOWN))
        {
            playerDirectionThisTick = Direction.DOWN;
            saveState();
        }
        else if (input.isKeyPressed(Input.KEY_LEFT))
        {
            playerDirectionThisTick = Direction.LEFT;
            saveState();
        }
        else if (input.isKeyPressed(Input.KEY_RIGHT))
        {
            playerDirectionThisTick = Direction.RIGHT;
            saveState();
        }
        else
        {
            playerDirectionThisTick = Direction.NONE;
        }
    }

    public void update(Input input, int delta) throws SlickException
    {
        // handle user wanting to undo or restart the level
        if (input.isKeyPressed(Input.KEY_Z)) undoState();
        if (input.isKeyPressed(Input.KEY_R)) restartLevel();

        // if our player has moved, save the game and set the player moved this
        // tick flag before updating our smartSprites
        watchForPlayerInput(input);

        // now that we know if a player has moved or not, update our smartSprites
        for (SmartSprite smartSprite : curGameState.getSmartSprites())
        {
            smartSprite.update(this, input, delta);
        }

        // check if user has completed the level
        if (nTargetTiles == curGameState.getnTargetTilesCovered()) levelComplete = true;
    }

    public void render(Graphics graphics) {
        // redraw RegularSprites that never update over the course of a level
        for (RegularSprite regSprite : regularSprites) {
            regSprite.render(levelDimensions);
        }
        // redraw SmartSprites in the current GameState
        for (SmartSprite smartSprite : curGameState.getSmartSprites()) {
            smartSprite.render(levelDimensions);
        }
        // draw moves made 10px down 10px right from the top left corner
        graphics.drawString("Moves: " + movesMade, 10, 10);
    }

    public Direction getPlayerDirectionThisTick() {
        return playerDirectionThisTick;
    }

    public boolean getLevelComplete() {
        return levelComplete;
    }

    public GameState getCurGameState() { return curGameState; }
}

