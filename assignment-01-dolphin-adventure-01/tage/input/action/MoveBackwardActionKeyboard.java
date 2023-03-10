package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
import tage.GameObject;

/**
 * MoveBackward is an input action that moves the avatar or camera backward.
 * 
 * @author Matthew M.
 */
public class MoveBackwardActionKeyboard extends AbstractInputAction {

    private MyGame game; // contains the avatar and camera (the world)
    private GameObject avatar; // the avatar is the "main character" in the game
    private Camera camera; // the camera is the "eye" of the player

    /**
     * Arbitrary scaling factor to make the movement at a reasonable speed
     */
    private float movement_speed,
            movement_scale_factor = -(0.003f); // negative because we are moving backward

    public MoveBackwardActionKeyboard(MyGame game) {
        this.game = game;
        this.avatar = game.getAvatar();
        this.camera = game.getCameraMain();
    }

    @Override
    public void performAction(float time, Event evt) {
        movement_speed = (movement_scale_factor * time);
        /*
         * the analog stick is not being used to move
         * ANALOG_STICK_DEAD_ZONE is negative because we are moving backward
         */
        if (evt.getValue() >= -ANALOG_STICK_DEAD_ZONE && evt.getValue() <= ANALOG_STICK_DEAD_ZONE) {
            return;
        }

        if (game.isInFreeCamMode()) { // the camera is free to move around
            camera.moveBackward(movement_speed, game.isCameraInAvatarProximity());
        } else { // the camera is bound to the avatar
            avatar.moveBackward(movement_speed);
            game.positionCameraBehindAvatar();
        }
    }
}