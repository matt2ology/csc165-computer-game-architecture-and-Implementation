package tage.input.action;

import a1.MyGame;
import tage.Camera;
import tage.GameObject;

import net.java.games.input.Event;

/**
 * MoveForward is an input action that moves the avatar or camera forward.
 * 
 * @author Matthew M.
 */
public class MoveForwardActionKeyboard extends AbstractInputAction {

    private MyGame game;
    private GameObject avatar; // the avatar is the "main character" in the game
    private Camera camera;

    /**
     * Arbitrary scaling factor to make the avatar move at a reasonable speed
     */
    private float movement_speed,
            movement_scale_factor = 0.003f; // positive because we are moving forward

    public MoveForwardActionKeyboard(MyGame game) {
        this.game = game;
        this.avatar = game.getAvatar();
        this.camera = game.getCameraMain();
    }

    /*
     * scale the movement speed by the time since the last frame was
     * rendered (time) and the arbitrary scaling factor (movement_scale_factor)
     * to make the avatar move at a reasonable speed (movement_speed)
     */
    @Override
    public void performAction(float time, Event evt) {
        movement_speed = movement_scale_factor * time;
        // the analog stick is not being used to move
        if (evt.getValue() >= -ANALOG_STICK_DEAD_ZONE && evt.getValue() <= ANALOG_STICK_DEAD_ZONE) {
            return;
        }

        if (game.isInFreeCamMode()) { // the camera is free to move around
            camera.moveForward(movement_speed, game.isCameraInAvatarProximity());
        } else { // the camera is bound to the avatar
            avatar.moveForward(movement_speed);
            game.positionCameraBehindAvatar();
        }
    }
}