package project;

import java.io.Serializable;
import java.util.List;

/**
 * The GameState stores all mutable aspects of a level. It means that if we need
 * to save the level, we simply need to make a copy of the current GameState and
 * store it away. If we want to go back in time, simply replace the current
 * GameState with an older stored GameState.
 */
public class GameState implements Serializable
{
    private List<SmartSprite> smartSprites;
    private List<List<CrackedWall>> crackedWalls;
    private List<List<Block>> blocks;
    private TileCoord playerCoord;
    private int nTargetTilesCovered = 0;
    private boolean switchIsCovered;

    /**
     * Initialises a new GameState
     * @param smartSprites ArrayList of SmartSprites
     * @param crackedWalls 2D List of CrackedWalls
     * @param blocks       2D List of blocks
     * @param playerCoord  The position of the Player sprite
     * @param switchCoord  The position of the switch
     */
    public GameState(List<SmartSprite> smartSprites,
                     List<List<CrackedWall>> crackedWalls,
                     List<List<Block>> blocks,
                     TileCoord playerCoord,
                     TileCoord switchCoord)
    {
        this.smartSprites = smartSprites;
        this.crackedWalls = crackedWalls;
        this.blocks = blocks;
        this.playerCoord = playerCoord;
        if (playerCoord != null)
        {
            this.switchIsCovered = playerCoord.equals(switchCoord);
        }
    }

    /**
     * Increases the value of nTargetTilesCovered by 1
     */
    public void incrementTargetTilesCovered() {
        nTargetTilesCovered++;
    }

    /**
     * Decreases the value of nTargetTilesCovered by 1
     */
    public void decrementTargetTilesCovered() {
        nTargetTilesCovered--;
    }

    /**
     * @return ArrayList of SmartSprites stored in the GameState
     */
    public List<SmartSprite> getSmartSprites()
    {
        return smartSprites;
    }

    /**
     * Changes the player coordinate
     * @param playerCoord The new player coordinate
     */
    public void setPlayerCoord(TileCoord playerCoord) {
        this.playerCoord = playerCoord;
    }

    /**
     * @return 2D List of CrackedWalls stored in the GameState
     */
    public List<List<CrackedWall>> getCrackedWalls()
    {
        return crackedWalls;
    }

    /**
     * @return 2D List of Blocks stored in the GameState
     */
    public List<List<Block>> getBlocks()
    {
        return blocks;
    }

    /**
     * @return The PlayerCoord stored in the GameState
     */
    public TileCoord getPlayerCoord()
    {
        return playerCoord;
    }

    /**
     * @return The number of target tiles covered in the GameState
     */
    public int getnTargetTilesCovered()
    {
        return nTargetTilesCovered;
    }

    /**
     * @return Whether or not the Switch is covered in this GameState
     */
    public boolean getSwitchIsCovered()
    {
        return this.switchIsCovered;
    }

    /**
     * @param switchIsCovered Sets whether the Switch is covered in this GameState
     */
    public void setSwitchIsCovered(boolean switchIsCovered)
    {
        this.switchIsCovered = switchIsCovered;
    }
}
