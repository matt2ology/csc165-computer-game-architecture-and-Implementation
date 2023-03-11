package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
import tage.GameObject;

/**
 * YawAction is an input action that rotates around the y-axis.
 * Uses analog left stick input for yaw rotation movement.
 * @author Matthew M.
 */
public class MoveYawActionStick extends AbstractInputAction {

    private MyGame game;
    private GameObject avatar; // the avatar is the "main character" in the game
    private Camera camera; // the camera is the "eye" of the player

    public MoveYawActionStick(MyGame game) {
        this.game = game;
        this.avatar = game.getAvatar();
        this.camera = game.getCameraMain();
    }

    @Override
    public void performAction(float time, Event evt) {
        // dead zone check for analog stick
        if (evt.getValue() >= -ANALOG_STICK_DEAD_ZONE && evt.getValue() <= ANALOG_STICK_DEAD_ZONE) {
            return;
        }

        if (game.isInFreeCamMode()) { // the camera is free to move around
            camera.yaw(evt.getValue() * time);
        } else { // the camera is bound to the avatar
            avatar.yaw(evt.getValue() * time);
            game.positionCameraBehindAvatar();
        }
    }
}
