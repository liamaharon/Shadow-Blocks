package project;

import java.util.List;

public class GameState {
    private List<SmartSprite> smartSprites;
    private List<List<CrackedWall>> crackedWalls;
    private List<List<Block>> blocks;
    private TileCoord playerCoord;
    private int nTargetTilesCovered;
    private boolean switchIsCovered;

    public GameState(List<SmartSprite> smartSprites,
                     List<List<CrackedWall>> crackedWalls,
                     List<List<Block>> blocks,
                     TileCoord playerCoord) {
        this.smartSprites = smartSprites;
        this.crackedWalls = crackedWalls;
        this.blocks = blocks;
        this.playerCoord = playerCoord;
    }

}
