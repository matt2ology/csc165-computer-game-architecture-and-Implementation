package a1;

import tage.*;
import tage.input.InputManager; // tage.input is needed for input management (keyboard, mouse, gamepad, etc.)
import tage.shapes.*;

import java.lang.Math; // java.lang.Math is always needed for Math functions like sin, cos, etc.)
import java.awt.*; // java.awt is almost always needed for graphics and GUI elements
import java.awt.event.*; // java.awt.event is almost always needed for keyboard and mouse events and listeners
import java.io.*; // java.io is almost always needed for file input and output
import javax.swing.*; // javax.swing is almost always needed for GUI elements
import org.joml.*; // org.joml is almost always needed for 3D math and transformations

public class MyGame extends VariableFrameRateGame {
	private static Engine engine;

	private InputManager inputManager;

	private boolean paused = false;
	private int counter = 0;
	private double lastFrameTime, currFrameTime, elapsTime;

	private GameObject dol,
			worldAxisX,
			worldAxisY,
			worldAxisZ;
	private ObjShape dolS,
			worldAxisLineShapeX,
			worldAxisLineShapeY,
			worldAxisLineShapeZ;
	private TextureImage doltx;
	private Light light1;

	public MyGame() {
		super();

		System.out.println("\n--------------------------------------------------");
		System.out.println("press ESC or Button 4 (Y) to quit");
		// Controls!
		System.out.println("press W or Left Joystick to move forward");
		System.out.println("press S or Left Joystick to move backward");
		System.out.println("press A or Left Joystick to move left");
		System.out.println("press D or Left Joystick to move right");
		System.out.println("press Q or Right Joystick to yaw left");
		System.out.println("press E or Right Joystick to yaw right");
		System.out.println("press UP or Right Joystick to pitch up");
		System.out.println("press DOWN or Right Joystick to pitch down");
		System.out.println("press SPACE or Button 2 (A) to hop on a nearby dolphin");
		System.out.println("--------------------------------------------------");
	}

	public static void main(String[] args) {
		MyGame game = new MyGame();
		engine = new Engine(game);
		game.initializeSystem();
		game.game_loop();
	}

	@Override
	public void loadShapes() {
		float lineLength = 5.0f;
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
		doltx = new TextureImage("Dolphin_HighPolyUV.png");
	}

	@Override
	public void buildObjects() {
		Matrix4f initialTranslation, initialScale;

		// build dolphin in the center of the window
		dol = new GameObject(GameObject.root(), dolS, doltx);
		initialTranslation = (new Matrix4f()).translation(0, 0, 0);
		initialScale = (new Matrix4f()).scaling(3.0f);
		dol.setLocalTranslation(initialTranslation);
		dol.setLocalScale(initialScale);

		// Build World Axis
		worldAxisX = new GameObject(GameObject.root(), worldAxisLineShapeX);
		worldAxisY = new GameObject(GameObject.root(), worldAxisLineShapeY);
		worldAxisZ = new GameObject(GameObject.root(), worldAxisLineShapeZ);

		// Set world axis colors
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
		lastFrameTime = System.currentTimeMillis();
		currFrameTime = System.currentTimeMillis();
		elapsTime = 0.0;
		(engine.getRenderSystem()).setWindowDimensions(1900, 1000);

		// ------------- positioning the camera -------------
		(engine.getRenderSystem()
				.getViewport("MAIN")
				.getCamera())
				.setLocation(new Vector3f(0, 0, 5));
	}

	@Override
	public void update() { // rotate dolphin if not paused
		lastFrameTime = currFrameTime;
		currFrameTime = System.currentTimeMillis();
		if (!paused) {
			// 1000.0 is needed to convert from milliseconds to seconds
			elapsTime += (currFrameTime - lastFrameTime) / 1000.0;
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
			case KeyEvent.VK_4:
				(engine.getRenderSystem().getViewport("MAIN").getCamera())
						.setLocation(new Vector3f(0, 0, 0));
				break;
		}
		super.keyPressed(e);
	}
}