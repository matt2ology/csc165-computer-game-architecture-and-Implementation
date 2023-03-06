package tage;

import net.java.games.input.Event;
import org.joml.Math.*;
import tage.input.action.AbstractInputAction;
import org.joml.*;
import org.joml.Math;

import a1.MyGame;

/**
 * This class is used to interact with the avatar.
 * 
 * @author Matt
 */
public class InteractAvatar extends AbstractInputAction {
    private MyGame game;
    private GameObject character;
    private Camera camera;
    private Vector3f currentLocation;
    private Vector3f newLocation;
    private Vector3f forwardVector;
    private Vector3f upVector;
    private Vector3f rightVector;

    /**
     * Constructor for the InteractAvatar class.
     * 
     * @param myGame - the game that is being played (MyGame)
     */
    public InteractAvatar(MyGame myGame) {
        game = myGame;
        character = game.getAvatar();
        camera = game.getCameraMain();
    }

    @Override
    public void performAction(float time, Event evt) {
        game.toggleFreeCam();
        game.updateCameraMain(camera);
        if (!game.isFreeCam()) { // set the camera to the avatar
            currentLocation = character.getWorldLocation();
            forwardVector = character.getWorldForwardVector();
            upVector = character.getWorldUpVector();
            rightVector = character.getWorldRightVector();
            camera.setU(rightVector);
            camera.setV(upVector);
            camera.setN(forwardVector);
            camera.setLocation(currentLocation
                    .add(upVector.mul(1.3f))
                    .add(forwardVector.mul(-2.5f)));
        }
        else // set the camera bounds 
        {
            isCameraInBounds(camera.getLocation(), character.getWorldLocation());
        }
        System.err.println("camera location: " + camera.getLocation());
    }

    /**
     * Check the bounds of the camera to make sure it doesn't go too far from the
     * character
     * If it does, then move the camera back to the character
     * @param cameraDistance - the current value of the camera (the camera)
     * @param targetDistance - the target value of the camera (the character)
     * @return true if the camera is in bounds, false if it is not
     * @author Matt
     */
    private boolean isCameraInBounds(Vector3f cameraDistance, Vector3f targetDistance) {
        Vector3f boundingSphere = new Vector3f(1.5f, 1.5f, 1.5f);
        Vector3f cameraToTarget = new Vector3f();
        cameraToTarget = cameraDistance.sub(targetDistance, cameraToTarget);
        float distance = cameraToTarget.length();
        return (distance < boundingSphere.length()) ? true : false;
        // if (distance > boundingSphere.length()) {
        //     cameraToTarget.normalize();
        //     cameraToTarget.mul(boundingSphere);
        //     cameraToTarget.add(targetDistance);
        //     camera.setLocation(cameraToTarget);
        //     return false;
        // }
        // return true;
    }
}