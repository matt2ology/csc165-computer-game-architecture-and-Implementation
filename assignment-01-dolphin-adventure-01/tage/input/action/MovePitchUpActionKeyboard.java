package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
import tage.GameObject;

/**
 * MovePitch is an input action that moves the game object or camera up or down.
 * 
 * @author Matthew M.
 */
public class MovePitchUpActionKeyboard extends AbstractInputAction {
    private MyGame game;
    private GameObject avatar; // the avatar is the "main character" in the game
    private Camera camera;
    private float movement_speed,
            movement_scale_factor = 0.0003f; // positive because we are pitching upward

    public MovePitchUpActionKeyboard(MyGame game) {
        this.game = game;
        this.avatar = game.getAvatar();
        this.camera = game.getCameraMain();
    }

    @Override
    public void performAction(float time, Event evt) {
        movement_speed = movement_scale_factor * time;
        // the analog stick is not being used to move
        if (evt.getValue() >= -ANALOG_STICK_DEAD_ZONE && evt.getValue() <= ANALOG_STICK_DEAD_ZONE) {
            return;
        }

        if (game.isInFreeCamMode()) { // the camera is free to move around
            camera.pitch(movement_speed);
        } else { // the camera is bound to the avatar
            avatar.pitch(movement_speed);
            game.positionCameraBehindAvatar();
        }
    }
}