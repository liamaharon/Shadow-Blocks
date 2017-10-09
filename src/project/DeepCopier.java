package project;

import org.newdawn.slick.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class DeepCopier
{
    // make a deep copy of the latest gameState (curGameState) using
    // serialization
    // credit to https://alvinalexander.com/java/java-deep-clone-example-source-code
    // for example
    public static GameState deepCopyGameState(GameState initialGameState)
    {
        GameState newGameState = null;
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // create bytestream of the GameState
            oos.writeObject(initialGameState);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            // return the bytestream as a new GameState object
            ObjectInputStream ois = new ObjectInputStream(bais);
            newGameState = (GameState) ois.readObject();
            // because Image isn't serializable and we aren't allowed to use anymore
            // 3rd party libraries we needed to set our sprites images to
            // transient. this means whenever we deepcopy we lose the image, so before
            // returning a serialized GameState we need to go through it and reassign
            // images
            for (SmartSprite smartSprite : newGameState.getSmartSprites())
            {
                smartSprite.setImg(new Image(smartSprite.getImgSrc()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // return our deep copied gameState woooo!
        return newGameState;
    }
}
