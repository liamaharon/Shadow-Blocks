package project;

import java.util.List;

public class GameState
{
    private List<SmartSprite> smartSprites;
    private List<List<CrackedWall>> crackedWalls;
    private List<List<Block>> blocks;
    private TileCoord playerCoord;
    private int nTargetTilesCovered;
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

    public List<SmartSprite> getSmartSprites()
    {
        return smartSprites;
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

    public void setnTargetTilesCovered(int nTargetTilesCovered)
    {
        this.nTargetTilesCovered = nTargetTilesCovered;
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
