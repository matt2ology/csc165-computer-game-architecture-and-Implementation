package a1;

import java.awt.*; // java.awt is almost always needed for graphics and GUI elements
import java.awt.event.*; // java.awt.event is almost always needed for keyboard and mouse events and listeners
import java.io.*; // java.io is almost always needed for file input and output
import java.lang.Math; // java.lang.Math is always needed for Math functions like sin, cos, etc.)
import java.util.ArrayList; // java.util.ArrayList is almost always needed for lists of objects
import java.util.logging.Logger; // java.util.logging.Logger is always needed for logging
import javax.swing.*; // javax.swing is almost always needed for GUI elements
import net.java.games.input.Controller; // net.java.games.input.Controller is always needed for gamepad input
import org.joml.*; // org.joml is almost always needed for 3D math and transformations

import tage.*;
import tage.input.action.MoveBackwardActionKeyboard;
import tage.input.action.MoveForwardActionKeyboard;
import tage.input.action.MovePitchDownActionKeyboard;
import tage.input.action.MovePitchUpActionKeyboard;
import tage.input.action.MoveYawActionKeyboard;
import tage.input.InputManager; // tage.input is needed for input management (keyboard, mouse, gamepad, etc.)
import tage.shapes.*;

public class MyGame extends VariableFrameRateGame {
	public static Logger logging = Logger.getLogger(MyGame.class.getName());
	/**
	 * 1000 milliseconds in one second. Used for converting between time units.
	 */
	private static final double ONE_SECOND = 1000.0;
	/**
	 * The maximum distance the camera can be from the avatar.
	 */
	private static final int CAMERA_TETHER_DISTANCE = 5;

	private static Engine engine;

	private InputManager inputManager;
	private Vector3d cameraDistanceFromAvatar = new Vector3d(0, 0, 0);
	private Vector3f location, // object location in world coordinates
			forward, // object forward vector in world coordinates (n-vector/z-axis)
			up, // object up vector in world coordinates (v-vector/y-axis)
			right; // object right vector in world coordinates (u-vector/x-axis)

	/**
	 * Photomode and/or freecam/flycam mode
	 * - if true, the camera is not attached to the avatar
	 */
	private boolean freeCamMode = false;
	private boolean paused = false;
	private int counter = 0, frameCounter = 0;
	private double lastFrameTime,
			currFrameTime,
			elapsTime,
			framePerSecond = 0,
			fpsSum = 0, // fpsSum is the sum of the last 100 frames
			fpsAvg = 0;

	/**
	 * @return the average fps over the last 100 frames
	 * @author Matthew M.
	 */
	public double getFpsAvg() {
		return fpsAvg;
	}

	/**
	 * calculate the average fps over the last 100 frames
	 * (or less if there are less than 100 frames)
	 * 
	 * @param fpsAvg
	 * @author Matthew M.
	 */
	public void calculateAverageFPS() {
		if (frameCounter < 100) {
			// add the newest frame to the sum
			fpsSum += framePerSecond;
			fpsAvg = fpsSum / frameCounter;
		} else {
			// remove the oldest frame from the sum
			fpsSum -= fpsAvg;
			// add the newest frame to the sum
			fpsSum += framePerSecond;
			// calculate the average fps over the last 100 frames
			fpsAvg = fpsSum / 100;
		}
	}

	/**
	 * The camera is the "eye" of the player.
	 */
	private Camera cameraMain;
	private GameObject avatar, // the avatar is the "main character" in the game
			dol,
			worldAxisX,
			worldAxisY,
			worldAxisZ;

	private ObjShape dolS,
			worldAxisLineShapeX,
			worldAxisLineShapeY,
			worldAxisLineShapeZ;
	private TextureImage dolTX;
	private Light light1;

	public MyGame() {
		super();
		System.out.println("\n--------------------------------------------------");
		System.out.println("press ESC or Button 4 (Y) to quit");
		// Controls!
		System.out.println("press W or Left Joystick to move forward");
		System.out.println("press S or Left Joystick to move backward");
		System.out.println("press A or Right Joystick to yaw left");
		System.out.println("press D or Right Joystick to yaw right");
		System.out.println("press Q or Right Joystick to pitch up");
		System.out.println("press E or Right Joystick to pitch down");
		System.out.println("press SPACE or Button 2 (A) to hop on a nearby dolphin");
		System.out.println("--------------------------------------------------");
		System.err.println("FreeCam mode is " + (isInFreeCamMode() ? "ON" : "OFF"));
	}

	public static void main(String[] args) {
		MyGame game = new MyGame();
		engine = new Engine(game);
		game.initializeSystem();
		game.game_loop();
	}

	@Override
	public void loadShapes() {
		float lineLength = 100.0f;
		Vector3f worldOrigin = new Vector3f(0f, 0f, 0f);
		Vector3f lineX = new Vector3f(lineLength, 0f, 0f);
		Vector3f lineY = new Vector3f(0f, lineLength, 0f);
		Vector3f lineZ = new Vector3f(0f, 0f, -lineLength);

		dolS = new ImportedModel("dolphinHighPoly.obj");

		worldAxisLineShapeX = new Line(worldOrigin, lineX);
		worldAxisLineShapeY = new Line(worldOrigin, lineY);
		worldAxisLineShapeZ = new Line(worldOrigin, lineZ);
	}

	@Override
	public void loadTextures() {
		dolTX = new TextureImage("Dolphin_HighPolyUV.png");
	}

	@Override
	public void buildObjects() {
		float dolInitialAngDeg = 0.0f;
		Matrix4f dolInitialTranslation, dolInitialScale, dolInitialRotation;

		dolInitialTranslation = (new Matrix4f())
				.translation(0.0f, 0.0f, 0.0f);
		dolInitialScale = (new Matrix4f()).scaling(3.0f);
		dolInitialRotation = (new Matrix4f()).rotationY(
				(float) Math.toRadians(dolInitialAngDeg));

		// build dolphin in the center of the window
		dol = new GameObject(GameObject.root(), dolS, dolTX);
		dol.setLocalTranslation(dolInitialTranslation);
		dol.setLocalScale(dolInitialScale);
		dol.setLocalRotation(dolInitialRotation);

		// Build World Axis Lines (X, Y, Z) in the center of the window
		worldAxisX = new GameObject(GameObject.root(), worldAxisLineShapeX);
		worldAxisY = new GameObject(GameObject.root(), worldAxisLineShapeY);
		worldAxisZ = new GameObject(GameObject.root(), worldAxisLineShapeZ);

		// Set world axis colors (red, green, blue) - X, Y, Z respectively
		worldAxisX.getRenderStates().setColor(new Vector3f(1f, 0, 0));
		worldAxisY.getRenderStates().setColor(new Vector3f(0, 1f, 0));
		worldAxisZ.getRenderStates().setColor(new Vector3f(0, 0, 1f));
	}

	@Override
	public void initializeLights() {
		Light.setGlobalAmbient(0.5f, 0.5f, 0.5f);
		light1 = new Light();
		light1.setLocation(new Vector3f(5.0f, 4.0f, 2.0f));
		(engine.getSceneGraph()).addLight(light1);
	}

	@Override
	public void initializeGame() {
		cameraMain = (engine.getRenderSystem()
				.getViewport("MAIN")
				.getCamera());

		lastFrameTime = System.currentTimeMillis();
		currFrameTime = System.currentTimeMillis();
		elapsTime = 0.0;
		(engine.getRenderSystem()).setWindowDimensions(1900, 1000);

		setAvatar(dol); // The avatar is the object that the camera follows

		// ------------- positioning the camera -------------
		positionCameraBehindAvatar();
		unboundCameraFromAvatar();

		// ------------- Control Inputs -------------
		inputManager = engine.getInputManager();
		ArrayList<Controller> controllers = inputManager.getControllers(); // get all our controllers
		MoveForwardActionKeyboard moveForward = new MoveForwardActionKeyboard(this);
		MoveBackwardActionKeyboard moveBackward = new MoveBackwardActionKeyboard(this);
		MoveYawActionKeyboard yawLeft = new MoveYawActionKeyboard(this, 1);
		MoveYawActionKeyboard yawRight = new MoveYawActionKeyboard(this, -1);
		MovePitchUpActionKeyboard pitchUp = new MovePitchUpActionKeyboard(this);
		MovePitchDownActionKeyboard pitchDown = new MovePitchDownActionKeyboard(this);

		/*
		 * Gamepad Logitech F310 - Controller: Logitech Dual Action - Type: Stick
		 */
		for (Controller controller : controllers) {
			System.err.println("Controller: " + controller.getName());
			System.err.println("Type: " + controller.getType());
			if (controller.getType() == Controller.Type.STICK) {
				inputManager.associateAction(
						controller,
						net.java.games.input.Component.Identifier.Axis.Y,
						moveForward,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

				inputManager.associateAction(
						controller,
						net.java.games.input.Component.Identifier.Axis.X,
						yawLeft,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				// Y (left stick y-axis) = move backward
				inputManager.associateAction(
						controller,
						net.java.games.input.Component.Identifier.Axis.Y,
						moveBackward,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				// RX (right stick x-axis) = yaw right
				inputManager.associateAction(
						controller,
						net.java.games.input.Component.Identifier.Axis.RX,
						yawRight,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			} else if (controller.getType() == Controller.Type.KEYBOARD) {
				// W = move forward
				inputManager.associateActionWithAllKeyboards(
						net.java.games.input.Component.Identifier.Key.W,
						moveForward,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				// A = move left - yaw left
				inputManager.associateActionWithAllKeyboards(
						net.java.games.input.Component.Identifier.Key.A,
						yawLeft,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				// S = move backward
				inputManager.associateActionWithAllKeyboards(
						net.java.games.input.Component.Identifier.Key.S,
						moveBackward,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				// D = move right - yaw right
				inputManager.associateActionWithAllKeyboards(
						net.java.games.input.Component.Identifier.Key.D,
						yawRight,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				// Q = pitch up
				inputManager.associateActionWithAllKeyboards(
						net.java.games.input.Component.Identifier.Key.Q,
						pitchUp,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				// E = pitch down
				inputManager.associateActionWithAllKeyboards(
						net.java.games.input.Component.Identifier.Key.E,
						pitchDown,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			}
		}
	}

	@Override
	public void update() { // called every frame. Each loop is one frame.
		lastFrameTime = currFrameTime;
		currFrameTime = System.currentTimeMillis();
		if (!paused) {
			elapsTime += (currFrameTime - lastFrameTime) / ONE_SECOND;
		}

		// build and set HUD
		int elapsTimeSec = Math.round((float) elapsTime);
		String elapsTimeStr = Integer.toString(elapsTimeSec);
		String counterStr = Integer.toString(counter);
		String dispStr1 = "Time = " + elapsTimeStr;
		String dispStr2 = "Keyboard hits = " + counterStr;
		Vector3f hud1Color = new Vector3f(1, 0, 0);
		Vector3f hud2Color = new Vector3f(0, 0, 1);
		(engine.getHUDmanager()).setHUD1(dispStr1, hud1Color, 15, 15);
		(engine.getHUDmanager()).setHUD2(dispStr2, hud2Color, 500, 15);

		// Used FPS because it is more stable than the delta time method
		inputManager.update(getFramesPerSecond());
		frameCounter++;
	}

	/**
	 * Calculates the number of frames per second based on
	 * the elapsed time and the number of frames rendered.
	 * 
	 * @author Matthew M.
	 * @return The number of frames per second calculated
	 */
	private float getFramesPerSecond() {
		return (float) (frameCounter / elapsTime);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_C:
				counter++;
				break;
			case KeyEvent.VK_1:
				paused = !paused;
				break;
			case KeyEvent.VK_2:
				dol.getRenderStates().setWireframe(true);
				break;
			case KeyEvent.VK_3:
				dol.getRenderStates().setWireframe(false);
				break;
		}
		super.keyPressed(e);
	}

	/**
	 * @author Matthew M.
	 * @return The camera from the "MAIN" viewport
	 */
	public Camera getCameraMain() {
		return cameraMain;
	}

	/**
	 * @author Matthew M.
	 * @param cameraMain The camera to be update the "MAIN" viewport's camera
	 */
	public void updateCameraMain(Camera cameraMain) {
		this.cameraMain = cameraMain;
	}

	/**
	 * @author Matthew M.
	 * @return the freeCam boolean value (true or false)
	 */
	public boolean isInFreeCamMode() {
		return freeCamMode;
	}

	/**
	 * Toggles the freeCam boolean value to true or false
	 * 
	 * @author Matthew M.
	 */
	public void toggleFreeCam() {
		logging.info("freeCamMode : " + freeCamMode);
		this.freeCamMode = !freeCamMode;
		logging.info("toggled freeCamMode : " + freeCamMode);
		positionCameraBehindAvatar();
		unboundCameraFromAvatar();
	}

	/**
	 * @author Matthew M.
	 * @return the avatar GameObject (the "player"/"main character")
	 */
	public GameObject getAvatar() {
		return avatar;
	}

	/**
	 * @author Matthew M.
	 * @param avatar GameObject to be set as the avatar:
	 *               "player"/"main character"
	 */
	public void setAvatar(GameObject avatar) {
		this.avatar = avatar;
	}

	/**
	 * Position the camera behind the avatar (the "player"/"main character")
	 * Based on freeCam boolean value (true or false)
	 * 
	 * @author Matthew M.
	 */
	public void positionCameraBehindAvatar() {
		float distanceBehindTheAvatar = -4.5f;
		float heightAboveTheAvatar = 1.0f;
		if (!isInFreeCamMode()) {
			location = getAvatar().getWorldLocation();
			forward = getAvatar().getWorldForwardVector();
			up = getAvatar().getWorldUpVector();
			right = getAvatar().getWorldRightVector();
			getCameraMain().setU(right);
			getCameraMain().setV(up);
			getCameraMain().setN(forward);
			getCameraMain().setLocation(location
					.add(up.mul(heightAboveTheAvatar))
					.add(forward.mul(distanceBehindTheAvatar)));
		}
	}

	/**
	 * Unbind/dismount the camera from the avatar (the "player"/"main character")
	 * Based on freeCam boolean value (true or false)
	 * 
	 * @author Matthew M.
	 */
	public void unboundCameraFromAvatar() {
		float dismountDistanceOff = -5f;
		float dismountOffFromGround = 0.5f;
		if (isInFreeCamMode()) {
			location = getAvatar().getWorldLocation();
			forward = getAvatar().getWorldForwardVector();
			up = getAvatar().getWorldUpVector();
			right = getAvatar().getWorldRightVector();
			getCameraMain().setU(right);
			getCameraMain().setV(up);
			getCameraMain().setN(forward);
			getCameraMain().setLocation(location
					.add(up.mul(dismountOffFromGround))
					.add(forward.mul(dismountDistanceOff)));
		}
	}

	/**
	 * @author Matthew M.
	 * @return true if the camera is within the tether distance of the avatar
	 * @see cameraDistanceFromAvatar
	 */
	public boolean isCameraInAvatarProximity() {
		cameraDistanceFromAvatar.x = (Math.abs(
				getAvatar().getWorldLocation().x() - getCameraMain().getLocation().x()));
		cameraDistanceFromAvatar.y = (Math.abs(
				getAvatar().getWorldLocation().y() - getCameraMain().getLocation().y()));
		cameraDistanceFromAvatar.z = (Math.abs(
				getAvatar().getWorldLocation().z() - getCameraMain().getLocation().z()));
		return (cameraDistanceFromAvatar.length() < CAMERA_TETHER_DISTANCE)
				? true
				: false;
	}
}