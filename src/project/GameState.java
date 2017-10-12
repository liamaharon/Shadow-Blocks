package project;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable
{
    private List<SmartSprite> smartSprites;
    private List<List<CrackedWall>> crackedWalls;
    private List<List<Block>> blocks;
    private TileCoord playerCoord;
    private int nTargetTilesCovered = 0;
    private boolean switchIsCovered;

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
        this.switchIsCovered = playerCoord.equals(switchCoord);
    }

    public void incrementTargetTilesCovered() {
        nTargetTilesCovered++;
    }

    public void decrementTargetTilesCovered() {
        nTargetTilesCovered--;
    }

    public List<SmartSprite> getSmartSprites()
    {
        return smartSprites;
    }

    public void setPlayerCoord(TileCoord playerCoord) {
        this.playerCoord = playerCoord;
    }

    public List<List<CrackedWall>> getCrackedWalls()
    {
        return crackedWalls;
    }

    public List<List<Block>> getBlocks()
    {
        return blocks;
    }

    public TileCoord getPlayerCoord()
    {
        return playerCoord;
    }

    public int getnTargetTilesCovered()
    {
        return nTargetTilesCovered;
    }

    public boolean getSwitchIsCovered()
    {
        return this.switchIsCovered;
    }

    public void setSwitchIsCovered(boolean switchIsCovered)
    {
        this.switchIsCovered = switchIsCovered;
    }
}
