package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public class Player extends SmartSprite implements Serializable
{
    public Player (TileCoord pos) throws SlickException
    {
        super("res/player_left.png", pos);
    }



    // save game and move the player
    private void move(TileCoord desiredPos, LevelManager levelManager)
    {
        levelManager.saveState();
        setPos(desiredPos);
    }

    // handle a user trying to move the player, resulting in nothing happening
    // if the move is impossible or the player and potentially affected blocks
    // also moving
    private void tryMove(TileCoord desiredPos,
                         LevelManager levelManager,
                         Direction dir) throws SlickException
    {
        // Player sprites are always blocked by cracked walls, closed doors,
        // and walls. if blocked by any of these, do nothing.
        if (
                levelManager.tileIsBlockedByWall(desiredPos) ||
                levelManager.getCrackedWall(desiredPos) != null ||
                levelManager.tileIsBlockedByDoor(desiredPos)
            ) return;

        // calculate the position of the second tile over from where the player
        // is trying to move. this is the position a pushable block would be
        // moved to if it exists at desiredPos
        TileCoord secondTileOverPos = null;
        switch(dir)
        {
            case UP: secondTileOverPos = new TileCoord(desiredPos.getX(), desiredPos.getY()-1); break;
            case DOWN: secondTileOverPos = new TileCoord(desiredPos.getX(), desiredPos.getY()+1); break;
            case LEFT: secondTileOverPos = new TileCoord(desiredPos.getX()-1, desiredPos.getY()); break;
            case RIGHT: secondTileOverPos = new TileCoord(desiredPos.getX()+1, desiredPos.getY()); break;
        }
        // check if there's a tile in our desired position that is able to be
        // pushed in the direction we're going
        Block tileToPush = levelManager.getPushableTile(desiredPos);

        // if a pushable tile is in our desired position but it can't move in
        // the direction we want, do nothing
        if (
                tileToPush != null &&
                !tileToPush.canMoveTo(secondTileOverPos, levelManager)
           ) return;

        // at this point in we're able to move, either because nothing is in
        // the path of the player or a block is there but we can move it out
        // of the way. move the player, and move a block if there's one there
        move(desiredPos, levelManager);
        if (tileToPush != null)
        {
            tileToPush.move(secondTileOverPos, levelManager);
        }
    }

    @Override
    public void update(Input input, LevelManager levelManager) throws SlickException
    {
        TileCoord desiredPos;
        if (input.isKeyPressed(Input.KEY_UP))
        {
            desiredPos = new TileCoord(getPos().getX(), getPos().getY()-1);
            tryMove(desiredPos, levelManager, Direction.UP);
        }
        else if (input.isKeyPressed(Input.KEY_DOWN))
        {
            desiredPos = new TileCoord(getPos().getX(), getPos().getY()+1);
            tryMove(desiredPos, levelManager, Direction.DOWN);
        }
        else if (input.isKeyPressed(Input.KEY_LEFT))
        {
            desiredPos = new TileCoord(getPos().getX()-1, getPos().getY());
            tryMove(desiredPos, levelManager, Direction.LEFT);
        }
        else if (input.isKeyPressed(Input.KEY_RIGHT))
        {
            desiredPos = new TileCoord(getPos().getX()+1, getPos().getY());
            tryMove(desiredPos, levelManager, Direction.RIGHT);
        }
    }
}
