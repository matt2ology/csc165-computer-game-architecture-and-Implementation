package myGame;

import tage.*;
import tage.shapes.*;

import java.lang.Math; // java.lang.Math is always needed for Math functions like sin, cos, etc.)
import java.awt.*; // java.awt is almost always needed for graphics and GUI elements
import java.awt.event.*; // java.awt.event is almost always needed for keyboard and mouse events and listeners 
import java.io.*; // java.io is almost always needed for file input and output
import javax.swing.*; // javax.swing is almost always needed for GUI elements 
import org.joml.*; // org.joml is almost always needed for 3D math and transformations

public class DolphinAdventure01 extends VariableFrameRateGame {
	private static Engine engine;

	private boolean paused = false;
	private int counter = 0;
	private double lastFrameTime, currentFrameTime, elapsedTime;

	private GameObject dolphin;
	private ObjShape dolphinShape;
	private TextureImage dolphinTexture;
	private Light light01;

	public DolphinAdventure01() {
		super();
	}

	public static void main(String[] args) {
		DolphinAdventure01 game = new DolphinAdventure01();
		engine = new Engine(game);
		game.initializeSystem();
		game.game_loop();
	}

	@Override
	public void loadShapes() {
		dolphinShape = new ImportedModel("dolphinHighPoly.obj");
	}

	@Override
	public void loadTextures() {
		dolphinTexture = new TextureImage("Dolphin_HighPolyUV.png");
	}

	@Override
	public void buildObjects() {
		Matrix4f initialTranslation, initialScale;

		// build dolphin in the center of the window
		dolphin = new GameObject(GameObject.root(), dolphinShape, dolphinTexture);
		initialTranslation = (new Matrix4f()).translation(0, 0, 0);
		initialScale = (new Matrix4f()).scaling(3.0f);
		dolphin.setLocalTranslation(initialTranslation);
		dolphin.setLocalScale(initialScale);
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
		if (!paused)
			elapsedTime += (currentFrameTime - lastFrameTime) / 1000.0; // 1000.0 is needed to convert from milliseconds to seconds
		dolphin.setLocalRotation((new Matrix4f()).rotation((float) elapsedTime, 0, 1, 0));

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
		switch (e.getKeyCode()) {
			case KeyEvent.VK_C:
				counter++;
				break;
			case KeyEvent.VK_1:
				paused = !paused;
				break;
			case KeyEvent.VK_2:
				dolphin.getRenderStates().setWireframe(true);
				break;
			case KeyEvent.VK_3:
				dolphin.getRenderStates().setWireframe(false);
				break;
			case KeyEvent.VK_4:
				(engine.getRenderSystem().getViewport("MAIN").getCamera())
						.setLocation(new Vector3f(0, 0, 0));
				break;
		}
		super.keyPressed(e);
	}
}