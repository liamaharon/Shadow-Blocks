package project;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private int levelWidth;
    private int levelHeight;
    private int movesMade;
    private List<List<Boolean>> permBlockedTiles;
    private List<List<Boolean>> targetTiles;
    private int nTargetTiles;
    private List<RegularSprite> regularSprites;
    private TileCoord switchCoord;
    private List<GameState> gameStates;


    public LevelManager(int levelWidth,
                        int levelHeight,
                        List<List<Boolean>> permBlockedTiles,
                        List<List<Boolean>> targetTiles,
                        int nTargetTiles,
                        List<RegularSprite> regularSprites,
                        TileCoord switchCoord,
                        GameState initialGameState) {

        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.permBlockedTiles = permBlockedTiles;
        this.targetTiles = targetTiles;
        this.nTargetTiles = nTargetTiles;
        this.regularSprites = regularSprites;
        this.switchCoord = switchCoord;

        gameStates = new ArrayList<>();
        gameStates.add(initialGameState);
    }
}
