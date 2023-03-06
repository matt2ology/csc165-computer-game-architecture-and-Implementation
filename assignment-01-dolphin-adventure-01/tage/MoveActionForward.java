package tage;

import net.java.games.input.Event;
import tage.input.action.AbstractInputAction;
import org.joml.*;

import a1.MyGame;

/**
 * This class is used to move forward.
 * @author Matt
 */
public class MoveActionForward extends AbstractInputAction {
    private Camera camera;
    private MyGame game;
    private GameObject character;
    private Vector3f currentLocation;
    private Vector3f newLocation;
    private Vector3f forwardVector;

    /**
     * Constructor for the MoveForwardAction class.
     * This class is used to move the camera in "MAIN"
     * viewportforward.
     * @param game - the game that is being played (MyGame)
     */
    public MoveActionForward(MyGame myGame) {
        game = myGame;
        character = game.getAvatar();
        camera = game.getCameraMain();
    }

    /**
     * If the camera is on the character, then the character will move forward.
     * If the camera is not on the character, then the camera will move forward.
     */
    @Override
    public void performAction(float time, Event evt) {
        float forwardMovementSpeed = 0.2f;
        if (game.isFreeCam()) {
            forwardVector = character.getWorldForwardVector();
            currentLocation = character.getWorldLocation();
            newLocation = currentLocation.add(
                forwardVector.mul(forwardMovementSpeed));
            character.setLocalLocation(newLocation);
        }
        else { // just move the camera
            currentLocation = camera.getLocation();
            forwardVector = camera.getN();
            newLocation = currentLocation.add(
                forwardVector.mul(forwardMovementSpeed));
            camera.setLocation(newLocation);
        }
    }
}
