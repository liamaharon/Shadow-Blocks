package project;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.lang.*;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.SlickException;

/**
 * The Loader class is responsible for taking resources from non .java files
 * and turning them into Java objects.
 */
public class Loader
{
    private static final File LEVELS_FOLDER = new File("res/levels");

    /**
     * Looks in the LEVELS_FOLDER location and returns a stored File[] of all
     * the levels stored there.
     * Adapted from https://stackoverflow.com/a/42694706/7108044
     * @return Sorted by name File[] of all the levels in LEVELS_FOLDER
     */
    public static File[] getSortedLevels()
    {
        File[] levels = LEVELS_FOLDER.listFiles();
        assert(levels != null);
        Arrays.sort(levels, new Comparator<File>()
        {
            /**
             * Specifies how we want to sort the Files
             * @param f1 The 1st File to be compared
             * @param f2 The 2nd File to be compared
             * @return   -1 if 1st File < 2nd File, 0 if Files are equal, 1 if
             *           1st File > 2nd File
             */
            @Override
            public int compare(File f1, File f2)
            {
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


    /**
     * Takes a line in CSV form containing the details of a sprite and returns
     * a sprite with those properties
     * @param text           A line in CSV form representing a Sprite
     * @return RegularSprite A sprite with the properties detailed in the text
     */
    private static RegularSprite stringToSprite(String text) throws SlickException
    {
        // extract columns from csv line
        String[] spriteDetails = text.split(",");

        // sprite type in 0th entry
        String spriteType = spriteDetails[0];

        // sprite coords x,y in 1st and 2nd entry respectively
        int xPos = Integer.parseInt(spriteDetails[1]);
        int yPos = Integer.parseInt(spriteDetails[2]);
        TileCoord spritePos = new TileCoord(xPos, yPos);

        switch(spriteType)
        {
            case "wall": return new Wall(spritePos);
            case "stone": return new Stone(spritePos);
            case "target": return new Target(spritePos);
            case "player": return new Player(spritePos);
            case "switch": return new Switch(spritePos);
            case "cracked": return new CrackedWall(spritePos);
            case "door": return new Door(spritePos);
            case "floor": return new Floor(spritePos);
            case "ice": return new IcyStone(spritePos);
            case "mage": return new Mage(spritePos);
            case "rogue": return new Rogue(spritePos);
            case "skeleton": return new Skeleton(spritePos);
            case "tnt": return new Tnt(spritePos);
            default: throw new IllegalArgumentException("Unknown Sprite: " + spriteType);
        }
    }

    /**
     * Creates and initialises a LevelManager.
     * Adapted from Grok worksheet "Reading Files".
     * @param file The file containing the level
     * @return     A LevelManager containing all the properties specified in the
     *             input file
     */
    public static LevelManager loadLevel(File file) throws SlickException
    {
        String levelPath = file.getPath();
        String curLine;

        // attributes to initialise LevelManager
        int levelWidth = 0;
        int levelHeight = 0;
        int nTargetTiles = 0;
        List<RegularSprite> regularSprites = new ArrayList<>();
        List<List<Boolean>> permBlockedTiles = new ArrayList<>();
        List<List<Boolean>> targetTiles = new ArrayList<>();
        TileCoord switchCoord = null;
        TileCoord doorCoord = null;

        // attributes to initialise LevelManager's initial GameState
        // smartSprites needs to be CopyOnWrite because it can be modified
        // during traversal, for example removing an explosion effect.
        List<SmartSprite> smartSprites = new CopyOnWriteArrayList<>();
        List<List<CrackedWall>> crackedWalls = new ArrayList<>();
        List<List<Block>> blocks = new ArrayList<>();
        TileCoord playerCoord = null;

        try (BufferedReader br = new BufferedReader(new FileReader(levelPath)))
        {
            // first line is always dimensions in form 'x,y'. extract them.
            curLine = br.readLine();
            String[] dimensions = curLine.split(",");
            levelWidth = Integer.valueOf(dimensions[0]);
            levelHeight = Integer.valueOf(dimensions[1]);

            // now that we know dimensions, we can initialise all our 2D lists
            // that are of shape level dimensions
            initialise2DList(permBlockedTiles, levelWidth, levelHeight, false);
            initialise2DList(targetTiles, levelWidth, levelHeight, false);
            initialise2DList(crackedWalls, levelWidth, levelHeight, null);
            initialise2DList(blocks, levelWidth, levelHeight, null);

            // next lines give sprite details
            RegularSprite curSprite;
            while ((curLine = br.readLine()) != null)
            {
                // parse each sprite, reference it and/or it's properties
                // in necessary places
                curSprite = stringToSprite(curLine);

                if (curSprite instanceof  Player)
                {
                    playerCoord = curSprite.getPos();
                }
                else if (curSprite instanceof Switch)
                {
                    switchCoord = curSprite.getPos();
                }
                else if (curSprite instanceof Door)
                {
                    doorCoord = curSprite.getPos();
                }
                else if (curSprite instanceof CrackedWall)
                {
                    crackedWalls
                            .get(curSprite.getPos().getX())
                            .set(curSprite.getPos().getY(), (CrackedWall) curSprite);
                }
                else if (curSprite instanceof Target)
                {
                    nTargetTiles++;
                    targetTiles
                            .get(curSprite.getPos().getX())
                            .set(curSprite.getPos().getY(), true);
                }
                else if (curSprite instanceof Block)
                {
                    blocks
                            .get(curSprite.getPos().getX())
                            .set(curSprite.getPos().getY(), (Block) curSprite);
                }
                else if (curSprite instanceof Wall)
                {
                    permBlockedTiles
                            .get(curSprite.getPos().getX())
                            .set(curSprite.getPos().getY(), true);
                }

                if (curSprite instanceof SmartSprite)
                {
                    smartSprites.add((SmartSprite) curSprite);
                }
                else
                {
                    regularSprites.add(curSprite);
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        // because the Mage needs to be updated after the player, and it's loaded
        // into the smartSprite list before the player, I rearrange the list
        // here so the mage updates after the player. I understand I could
        // have avoided needing to do this by simply manually telling the mage
        // to move in my Player class, but I think this breaks encapsulation
        // and would prefer that classes are not concerned with when each other
        // need to move

        // search through smartSprites for a Mage, if we find one move it to
        // the end of the list behind Player
        for (SmartSprite ss : smartSprites)
        {
            if (ss.getClass().getSimpleName().equals("Mage"))
            {
                // take a copy of the Mage
                Mage mage = (Mage) ss;
                // remove mage from its position and append it to the back of
                // the list
                smartSprites.remove(mage);
                smartSprites.add(mage);
            }
        }

        // setup and return our LevelManager
        TileCoord levelDimensions = new TileCoord(levelWidth, levelHeight);
        GameState initialGameState = new GameState(smartSprites,
                                                   crackedWalls,
                                                   blocks,
                                                   playerCoord,
                                                   switchCoord);
        return new LevelManager(levelDimensions,
                                permBlockedTiles,
                                targetTiles,
                                nTargetTiles,
                                regularSprites,
                                switchCoord,
                                doorCoord,
                                initialGameState);
    }

    /**
     * Takes an uninitialised 2D list and initialises it to the specified size,
     * with the specified initial values
     * @param list       The 2D list to be initialised
     * @param xMax       The x size we want to initialise to
     * @param yMax       The y size we want to initialise to
     * @param initialVal The initial value we want to fill the List with
     * @param <T>        The type that the List will be holding
     */
    private static <T> void initialise2DList(List<List<T>> list,
                                             int xMax,
                                             int yMax,
                                             T initialVal)
    {
        for (int i=0; i<xMax; i++) {
            list.add(new ArrayList<>());
            for (int j=0; j<yMax; j++) {
                list.get(i).add(initialVal);
            }
        }
    }
}

