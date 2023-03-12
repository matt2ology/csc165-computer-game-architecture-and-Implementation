package MyGame; // MyGame is the name of the package for this game

import tage.*; // TAGE is almost always needed for Engine, GameObject, etc.
import tage.shapes.*; // tage.shapes is almost always needed for shapes (ObjShape, ImportedModel, etc.)

// (other imports are usually also needed - org.joml is almost always needed)
import java.awt.*; // java.awt is almost always needed for graphics and GUI elements (Color, Graphics, etc.)
import java.awt.event.*; // java.awt.event is almost always needed for keyboard and mouse events and listeners (keyPressed, keyReleased, etc.)
import java.io.*; // java.io is almost always needed for file input and output (File, FileReader, BufferedReader, etc.)
import java.lang.Math; // java.lang.Math is always needed for Math functions like sin, cos, etc.)
import javax.swing.*; // javax.swing is almost always needed for GUI elements (JFrame, JPanel, etc.)
import org.joml.*; // org.joml is almost always needed for 3D math and transformations

public class MyGame extends VariableFrameRateGame {
    private static Engine engine;

    public static Engine getEngine() {
        return engine;
    }

    private GameObject dolphin; // GameObject variable declarations go here
    private ObjShape dolphinShape; // ObjShape variable declarations go here
    private TextureImage dolphinTexture; // TextureImage variable declarations go here
    private Light light01; // Light variable declarations go here

    public MyGame() {
        super();
    }

    public static void main(String[] args) {
        MyGame game = new MyGame();
        engine = new Engine(game);
        game.initializeSystem();
        game.game_loop();
    }

    @Override
    public void loadShapes() {
        // Instantiation of shapes go here
    }

    @Override
    public void loadTextures() {
        // Instantiation and loading of textures go here
    }

    @Override
    public void buildObjects() {
        // Initial creation of game objects here.
        // Each game object typically has a shape and a texture.
    }

    @Override
    public void initializeLights() {
        // Initialization of global ambient and other lights
    }

    @Override
    public void initializeGame() {
        // Initialization of other items done here.
        // This usually at least includes HUD and camera(s)
    }

    @Override
    public void update() {
        // This is automatically called once per frame. Therefore,
        // all game logic goes here. HUD updates also go here.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Desired keyboard assignments go here
        }
        super.keyPressed(e); // ESC already mapped to game exit.
    } // "=" key already mapped to window/fullscreen toggle
}