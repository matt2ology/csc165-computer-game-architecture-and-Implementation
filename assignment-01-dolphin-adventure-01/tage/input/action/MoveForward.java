package tage.input.action;

import a1.MyGame;
import tage.Camera;
import tage.GameObject;

import net.java.games.input.Event;

/**
 * MoveForward is an input action that moves the avatar or camera forward.
 * 
 * @author Matt
 */
public class MoveForward extends AbstractInputAction {

    private MyGame game;
    private GameObject avatar; // the avatar is the "main character" in the game
    private Camera camera;

    /**
     * Arbitrary scaling factor to make the avatar move at a reasonable speed
     */
    private float movement_scale_factor = 0.01f;

    public MoveForward(MyGame game) {
        this.game = game;
        this.avatar = game.getAvatar();
        this.camera = game.getCameraMain();
    }

    @Override
    public void performAction(float time, Event evt) {
        // the thumbstick is not being used to move the avatar
        if (Math.abs(evt.getValue()) < arbitraryThumbStickDeadZone) {
            return;
        }

        if (game.isInFreeCamMode()) { // the camera is free to move around
            camera.moveForward(time * movement_scale_factor, game.isCameraInAvatarProximity());
        } else { // the camera is bound to the avatar
            avatar.moveForward(time * movement_scale_factor);
            game.positionCameraBehindAvatar();
        }
    }
}