package tage;

import net.java.games.input.Event;
import tage.input.action.AbstractInputAction;
import org.joml.*;

import a1.MyGame;

/**
 * This class is used to move backwards.
 * @author Matt
 */
public class MoveActionBackward extends AbstractInputAction {
    private Camera camera;
    private MyGame game;
    private GameObject character;
    private Vector3f currentLocation;
    private Vector3f newLocation;
    private Vector3f forwardVector;

    /**
     * Constructor for the MoveActionBackward class.
     * This class is used to move the camera in "MAIN"
     * viewport backward.
     * @param game - the game that is being played (MyGame)
     */
    public MoveActionBackward(MyGame myGame) {
        game = myGame;
        character = game.getAvatar();
        camera = game.getCameraMain();
    }

    /**
     * If the camera is on the character, then the character will move backward.
     * If the camera is not on the character, then the camera will move backward.
     */
    @Override
    public void performAction(float time, Event evt) {
        float backwardMovementSpeed = -0.2f;
        if (game.isFreeCam()) {
            forwardVector = character.getWorldForwardVector();
            currentLocation = character.getWorldLocation();
            newLocation = currentLocation.add(
                forwardVector.mul(backwardMovementSpeed));
            character.setLocalLocation(newLocation);
        }
        else { // just move the camera
            currentLocation = camera.getLocation();
            forwardVector = camera.getN();
            newLocation = currentLocation.add(
                forwardVector.mul(backwardMovementSpeed));
            camera.setLocation(newLocation);
        }
    }
}
