package project;

import org.newdawn.slick.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The DeepCopier uses serialization to deep copy complex Objects.
 * Credit to https://alvinalexander.com/java/java-deep-clone-example-source-code
 * for giving an example on how to use serialization
 */
public final class DeepCopier
{
    /**
     * Using serialization, makes and returns a deep copy of a supplied
     * GameState
     * @param initialGameState The GameState to be copied
     * @return                 A deepcopy of the supplied GameState
     */
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
            // because Image isn't serializable and we aren't allowed to use
            // anymore 3rd party libraries we needed to set our sprites images to
            // transient. this means whenever we deepcopy we lose the image,
            // so before returning a serialized GameState we need to go through
            // it and reassign images
            for (SmartSprite smartSprite : newGameState.getSmartSprites())
            {
                smartSprite.setImg(new Image(smartSprite.getImgSrc()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // return our deep copied gameState
        return newGameState;
    }
}
