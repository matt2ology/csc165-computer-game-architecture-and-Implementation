package myGame;

import tage.*;
import tage.shapes.*;
// (other imports are usually also needed - org.joml is almost always needed)
public class MyGame extends VariableFrameRateGame
{
private static Engine engine;
public static Engine getEngine() { return engine; }
private GameObject xxx, xxx, ... GameObject variable declarations go here
private ObjShape xxx, xxx, ... ObjShape variable declarations go here
private TextureImage xxx, xxx, ... TextureImage variable declarations go here
private Light xxx, xxx, ... Light variable declarations go here
...
public MyGame() { super(); }
public static void main(String[] args)
{ MyGame game = new MyGame();
engine = new Engine(game);
game.initializeSystem();
game.game_loop();
}
@Override
public void loadShapes()
{ ... instantiation of shapes go here
}
@Override
public void loadTextures()
{ ... instantiation and loading of textures go here
}
@Override
public void buildObjects()
{ ... initial creation of game objects here.
Each game object typically has a shape and a texture.
}
@Override
public void initializeLights()
{ ... initialization of global ambient and other lights
}
@Override
public void initializeGame()
{ ... initialization of other items done here.
This usually at least includes HUD and camera(s)
}
@Override
public void update()
{ ... This is automatically called once per frame.
Therefore, all game logic goes here.
HUD updates also go here.
}
@Override
public void keyPressed(KeyEvent e)
{ switch (e.getKeyCode())
{ ... Desired keyboard assignments go here
}
super.keyPressed(e); ESC already mapped to game exit.
} "=" key already mapped to window/fullscreen toggle
}