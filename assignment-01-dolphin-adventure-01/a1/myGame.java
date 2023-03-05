package a1;

import tage.*;
import tage.shapes.*;

import java.lang.Math; // java.lang.Math is always needed for Math functions like sin, cos, etc.)
import java.awt.*; // java.awt is almost always needed for graphics and GUI elements
import java.awt.event.*; // java.awt.event is almost always needed for keyboard and mouse events and listeners 
import java.io.*; // java.io is almost always needed for file input and output
import javax.swing.*; // javax.swing is almost always needed for GUI elements 
import org.joml.*; // org.joml is almost always needed for 3D math and transformations

public class myGame extends VariableFrameRateGame {
	private static Engine engine;

	private boolean paused = false;
	private int counter = 0;
	private double lastFrameTime, currentFrameTime, elapsedTime;

	private GameObject worldAxeX, worldAxeY, worldAxeZ;
	private ObjShape worldLineX, worldLineY, worldLineZ;

	private GameObject dol;
	private ObjShape dolphinShape;
	private TextureImage dolphinTexture;
	private Light light01;

	private GameObject block;
	private ObjShape blockShape;
	private TextureImage blockTexture;

	public myGame() {
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
		myGame game = new myGame();
		engine = new Engine(game);
		game.initializeSystem();
		game.game_loop();
	}

	@Override
	public void loadShapes() {
		float lineLength = 10f;

		dolphinShape = new ImportedModel("dolphinHighPoly.obj");
		blockShape = new Cube();

		// build world axes
		worldLineX = new Line(
				new Vector3f(0, 0, 0),
				new Vector3f(lineLength, 0, 0));
		worldLineY = new Line(
				new Vector3f(0, 0, 0),
				new Vector3f(0, lineLength, 0));
		worldLineZ = new Line(
				new Vector3f(0, 0, 0),
				new Vector3f(0, 0, -lineLength));

	}

	@Override
	public void loadTextures() {
		dolphinTexture = new TextureImage("Dolphin_HighPolyUV.png");
		blockTexture = new TextureImage("blockTexture.jpg");
	}

	@Override
	public void buildObjects() {
		Matrix4f initialTranslation_dolphin,
				initialScale_dolphin,
				initialTranslation_block,
				initialScale_block;

		// create world axes
		worldAxeX = new GameObject(GameObject.root(), worldLineX);
		worldAxeY = new GameObject(GameObject.root(), worldLineY);
		worldAxeZ = new GameObject(GameObject.root(), worldLineZ);

		// set world axes' colors
		worldAxeX.getRenderStates().setColor(new Vector3f(1f, 0f, 0f));
		worldAxeY.getRenderStates().setColor(new Vector3f(0f, 1f, 0f));
		worldAxeZ.getRenderStates().setColor(new Vector3f(0f, 0f, 1f));

		// build dolphin in the center of the window
		dol = new GameObject(GameObject.root(), dolphinShape, dolphinTexture);
		initialTranslation_dolphin = (new Matrix4f()).translation(0, 0, 0);
		initialScale_dolphin = (new Matrix4f()).scaling(3.0f);
		dol.setLocalTranslation(initialTranslation_dolphin);
		dol.setLocalScale(initialScale_dolphin);

		// build block
		block = new GameObject(GameObject.root(), blockShape, blockTexture);
		initialTranslation_block = (new Matrix4f()).translation(3, 0, 0);
		initialScale_block = (new Matrix4f()).scaling(0.5f);
		block.setLocalTranslation(initialTranslation_block);
		block.setLocalScale(initialScale_block);

	}

	@Override
	public void initializeLights() {
		Light.setGlobalAmbient(0.5f, 0.5f, 0.5f);
		light01 = new Light();
		light01.setLocation(new Vector3f(5.0f, 4.0f, 2.0f));
		(engine.getSceneGraph()).addLight(light01);
	}

	@Override
	public void initializeGame() {
		lastFrameTime = System.currentTimeMillis();
		currentFrameTime = System.currentTimeMillis();
		elapsedTime = 0.0;
		(engine.getRenderSystem()).setWindowDimensions(1900, 1000);

		// ------------- positioning the camera -------------
		(engine.getRenderSystem()
				.getViewport("MAIN")
				.getCamera())
				.setLocation(new Vector3f(0, 0, 5));
	}

	@Override
	public void update() { // rotate dolphin if not paused
		lastFrameTime = currentFrameTime;
		currentFrameTime = System.currentTimeMillis();
		if (!paused) {
			// 1000.0 is needed to convert from milliseconds to seconds
			elapsedTime += (currentFrameTime - lastFrameTime) / 1000.0;
		}

		// build and set HUD
		int elapsedTimeSec = Math.round((float) elapsedTime);
		String elapsedTimeStr = Integer.toString(elapsedTimeSec);
		String counterStr = Integer.toString(counter);
		String displayStrElapsedTime = "Time = " + elapsedTimeStr;
		String displayStrKeyboardHitsCounter = "Keyboard hits = " + counterStr;
		Vector3f hud1Color = new Vector3f(1, 0, 0); // red color for HUD1 text
		Vector3f hud2Color = new Vector3f(0, 0, 1); // blue color for HUD2 text
		(engine.getHUDmanager()).setHUD1(displayStrElapsedTime, hud1Color, 15, 15);
		(engine.getHUDmanager()).setHUD2(displayStrKeyboardHitsCounter, hud2Color, 500, 15);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		Vector3f loc, fwd, up, right, newLocation;

		Camera camera;

		switch (e.getKeyCode()) {
			case KeyEvent.VK_1:
				paused = !paused;
				break;
			case KeyEvent.VK_W: // move dolphin forward
				fwd = dol.getWorldForwardVector();
				loc = dol.getWorldLocation();
				newLocation = loc.add(fwd.mul(0.2f));
				dol.setLocalLocation(newLocation);
				break;
			case KeyEvent.VK_S: // move dolphin backward
				fwd = dol.getWorldForwardVector();
				loc = dol.getWorldLocation();
				newLocation = loc.add(fwd.mul(-0.2f));
				dol.setLocalLocation(newLocation);
				break;
			case KeyEvent.VK_4: // view from dolphin's perspective
				camera = (engine.getRenderSystem()
						.getViewport("MAIN").getCamera());
				loc = dol.getWorldLocation();
				fwd = dol.getWorldForwardVector();
				up = dol.getWorldUpVector();
				right = dol.getWorldRightVector();
				camera.setU(right);
				camera.setV(up);
				camera.setN(fwd);
				camera.setLocation(
						loc.add(up.mul(1.3f))
								.add(fwd.mul(-2.5f)));
				break;
		}
		super.keyPressed(e);
	}
}
