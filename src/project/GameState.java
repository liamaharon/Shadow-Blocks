package project;

import java.util.List;

public class GameState {
    private List<SmartSprite> smartSprites;
    private List<CrackedWall> crackedWalls;
    private List<Block> blocks;
    private TileCoord playerCoordinate;
    private int nTargetTilesCovered;
    private boolean switchIsCovered;


}
