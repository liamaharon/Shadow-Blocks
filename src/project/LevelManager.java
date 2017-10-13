package project;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.LinkedList;
import java.util.List;

/**
 * A LevelManager is responsible for managing all aspects of a single level
 */
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

    /**
     * Initialise a LevelManager with supplied attributes
     * @param levelDimensions  The dimensions of the level
     * @param permBlockedTiles A 2D List of any permanently blocked tiles in the
     *                         level
     * @param targetTiles      A 2D List of booleans representing of there is a
     *                         target tile at any coordinate
     * @param nTargetTiles     How many target tiles there are in the level
     * @param regularSprites   A List of RegularSprites in the level
     * @param switchCoord      The coordinate of the Switch in this level(if any)
     * @param doorCoord        The coordinate of the Door in this level (if any)
     * @param initialGameState The level's initial GameState
     */
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

    /**
     * Take a copy of the current GameState and put it in the second last
     * position in the LinkedList behind curGameState. This means if we want
     * to restore back to the point before the save we simply pop the top of the
     * LinkedList and the new top is the last save. Also increment moves made.
     */
    public void saveState()
    {
        int indexToSave = gameStates.size()-1;
        gameStates.add(indexToSave, DeepCopier.deepCopyGameState(curGameState));
        movesMade++;
    }

    /**
     * If possible, pops the last item off gameStates, making the new tail the
     * GameState directly before the last save. Also decrement movesMade.
     * curGameState, decrement movesMade
     */
    private void undoState()
    {
        if (movesMade > 0 && gameStates.size() > 0)
        {
            gameStates.removeLast();
            curGameState = gameStates.getLast();
            movesMade--;
        }
    }

    /**
     * Remove everything from gameStates, and re-add the initial GameState
     * effectively setting everything up the way it was when the level first
     * was loaded
     */
    public void restartLevel()
    {
        gameStates.clear();
        // keep the initial GameState guaranteed pristine by never directly
        // adding it to gameStates where it could be mutated
        gameStates.add(DeepCopier.deepCopyGameState(initialGameState));
        curGameState = gameStates.getFirst();
        movesMade = 0;
    }

    /**
     * Returns if a tile is permanently blocked by a wall
     * @param pos The tile being checked
     * @return    If the tile is permanently blocked by a wall or not
     */
    public boolean tileIsBlockedByWall(TileCoord pos)
    {
        return permBlockedTiles
                .get(pos.getX())
                .get(pos.getY());
    }

    /**
     * Returns a reference to a Crackedwall in a coord.
     * @param pos The position being checked for a CrackedWall
     * @return    The CrackedWall from that position, or null if there is no
     *            wall there.
     */
    public CrackedWall getCrackedWallFromCoord(TileCoord pos)
    {
        return curGameState
                .getCrackedWalls()
                .get(pos.getX())
                .get(pos.getY());
    }

    /**
     * Returns if a position is blocked by a Door (Door must be closed)
     * @param pos Position being checked
     * @return    If the position is blocked by a Door
     */
    public boolean tileIsBlockedByDoor(TileCoord pos)
    {
        return  doorCoord != null &&
                doorCoord.equals(pos) &&
                !curGameState.getSwitchIsCovered();
    }

    /**
     * Checks if a tile is blocked by a pushable Block that is itself blocked
     * @param pos The position of the Block being checked
     * @param dir The directon we're checking is it possible to move the Block
     * @return    If the tile is blocked by a pushable Block that is itself
     *            blocked
     */
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

    /**
     * Returns a ref to a Block at the specified coord, if any
     * @param pos The position being checked
     * @return    A reference to the Block, or null if there is no block
     */
    public Block getBlockFromCoord(TileCoord pos)
    {
        return curGameState
                .getBlocks()
                .get(pos.getX())
                .get(pos.getY());
    }

    /**
     * Computes the position of a TileCoord directly adjacent to the TileCoord
     * specified, in the direction specified
     * @param pos Position to find adjacent TileCoord to
     * @param dir Direction to look from the supplied position
     * @return    TileCoord adjacent to the specified TileCoord in the specified
     *            direction
     */
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

    /**
     * Moves a Block from one place to another in the current GameStates 2D
     * List of Blocks. Also update nTilesCovered && switchIsCovered if
     * required.
     * @param prev The position a block is moving from
     * @param cur  The position a block is moving to
     */
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

    /**
     * Entirely removes a SmartSprite from the current GameState
     * @param smartSprite A reference to the SmartSprite to be removed
     */
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

    /**
     * Create a new Explosion sprite at the specified position and add it to
     * the current GameState
     * @param pos The position to create the Explosion
     */
    public void createExplosion(TileCoord pos) throws SlickException
    {
        curGameState.getSmartSprites().add(new Explosion(pos));
    }

    /**
     * Checks for any input that would effect the Player sprite, and records if
     * there was any. This needs to be done before updating any SmartSprites
     * because some sprites updates depend on the Player having updated before
     * the Player itself is updated, and also we need to save the GameState
     * if the Player wants to move before any sprites update.
     * @param input Object we can detect player input from
     */
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

    /**
     * Main update method called by the World. Checks if the user wants to undo
     * or restart the level, then checks for player input and updates all
     * SmartSprites in the current GameState. It then checks if the player has
     * won the level or not.
     * @param input Object we can detect user input from
     * @param delta Time in ms since the last update
     */
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

    /**
     * Renders the entire level
     * @param graphics Object we can draw custom things with, like a String
     */
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

    /**
     * @return The Player's direction moved this tick
     */
    public Direction getPlayerDirectionThisTick() {
        return playerDirectionThisTick;
    }

    /**
     * @return If the level is completed or not
     */
    public boolean getLevelComplete() {
        return levelComplete;
    }

    /**
     * @return The current level's GameState
     */
    public GameState getCurGameState() { return curGameState; }
}

