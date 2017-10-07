package project;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.newdawn.slick.SlickException;

public class Loader {
    private static int [] worldDimensions;
    private static final File LEVELS_FOLDER = new File("res/levels");
    // stores a 2D list of booleans, representing the solidity of each tile in the world
    private static List<List<Boolean>> tileSolidity = new ArrayList<>();
    // list of sprites loaded from loadSpritesFromFile
    private static List<Sprite> sprites = new ArrayList<>();

    // Adapted from https://stackoverflow.com/a/42694706/7108044
    public static File[] getSortedLevels() {
        File[] levels = LEVELS_FOLDER.listFiles();
        Arrays.sort(levels, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                String name1 = f1.getName();
                String name2 = f2.getName();
                // Get the level number of each file by slicing off the last
                // 4 chars (.lvl) and compare them using this level number
                String level1 = name1.substring(0, name1.length() - 4);
                String level2 = name2.substring(0, name2.length() - 4);
                Integer num1 = Integer.valueOf(level1);
                Integer num2 = Integer.valueOf(level2);
                return num1.compareTo(num2);
            }
        });
        return levels;
    }
    // takes a line in CSV form containing the details of a sprite and returns a sprite with those properties
    private static Sprite stringToSprite(String text) throws SlickException {
        // extract columns from csv line
        String[] spriteDetails = text.split(",");

        // sprite type in 0th entry
        String spriteType = spriteDetails[0];
        // sprite coords x,y in 1st and 2nd entry respectively
        int xPos = Integer.parseInt(spriteDetails[1]);
        int yPos = Integer.parseInt(spriteDetails[2]);
        // check sprite is not being created outside the game world
        if (xPos >= worldDimensions[0] || yPos >= worldDimensions[1]) {
            throw new IllegalArgumentException("Sprite coordinates out of bounds");
        }

        TileCoord spriteCoord = new TileCoord(xPos, yPos);

        switch(spriteType) {
            case "floor": return new Floor(spriteCoord);
            case "wall": return new Wall(spriteCoord);
            case "stone": return new Stone(spriteCoord);
            case "target": return new Target(spriteCoord);
            case "player": return new Player(spriteCoord);
            case "switch": return new Switch(spriteCoord);
            default: throw new IllegalArgumentException("Unknown Sprite: " + spriteType);
        }
    }

    /**
     * Creates and initialises a LevelManager.
     * Adapted from Grok worksheet "Reading Files".
     */
    public static LevelManager loadLevel(File file) {
        String levelPath = file.getPath();
        String curLine;
        RegularSprite curSprite;
        int levelWidth;
        int levelHeight;
        try (BufferedReader br = new BufferedReader(new FileReader(levelPath))) {

            // first line is always dimensions in form 'x,y'. extract them.
            curLine = br.readLine();
            String[] dimensions = curLine.split(",");
            levelWidth = Integer.valueOf(dimensions[0]);
            levelHeight = Integer.valueOf(dimensions[1]);

            // TODO initialise nested lists here

            // next lines give sprite details. parse each sprite adding to the world object and updating if it's solid
            while ((curLine = br.readLine()) != null) {
                // parse sprite, update the solidity of the world, add the sprite to the sprite list
                curSprite = stringToSprite(curLine);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new LevelManager();
    }

    private static void parseLevelLine(String line) {

    }


//
//        try(FileInputStream inputStream)
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//            String text;
//            Sprite sprite;
//
//            // first line is always dimensions
//            text = br.readLine();
//            String[] dimensions = text.split(",");
//            worldDimensions[0] = Integer.parseInt(dimensions[0]);
//            worldDimensions[1] = Integer.parseInt(dimensions[1]);
//            // now that we know the dimensions of the world, initialise the list detailing if a tile is blocked
//            initialiseTileSolidity();
//
//            // next lines give sprite details. parse each sprite adding to the world object and updating if it's solid
//            while ((text = br.readLine()) != null) {
//                // parse sprite, update the solidity of the world, add the sprite to the sprite list
//                sprite = stringToSprite(text);
//                updateTileSolidity(sprite);
//                sprites.add(sprite);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sprites;

    // returns true if tile is inaccessible to the player, due to being out of bounds or blocked by a wall
    public static boolean isBlocked(int xPos, int yPos) {
        return (
            xPos < 0 ||
            yPos < 0 ||
            xPos >= worldDimensions[0] ||
            yPos >= worldDimensions[1] ||
            tileSolidity.get(xPos).get(yPos)
        );
    }

    // if sprite is not a wall, unsolidify its tile
    private static void updateTileSolidity(Sprite s) {
        if (!(s instanceof Wall)) {
            tileSolidity
                    .get(s.getxPos())
                    .set(s.getyPos(), false);
        }
    }

    // initialises every tile to blocked
    private static void initialiseTileSolidity() {
        int xMax = worldDimensions[0];
        int yMax = worldDimensions[1];

        for (int i=0; i<xMax; i++) {
            tileSolidity.add(new ArrayList<>());
            for (int j=0; j<yMax; j++) {
                tileSolidity.get(i).add(true);
            }
        }
    }
}
