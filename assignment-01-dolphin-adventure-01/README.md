# Assignment #1:  Building a Game and Extending a Game Engine - “Dolphin Adventure 1”

Due: Friday Feb 17th  (3 weeks)

The objective of this assignment is to learn how to use and extend game engine
components to build a very simple 3D game. You will be relying on existing game
engine components to supply much of the underlying functionality, while
implementing some of the functionality yourself. The assignment requires you to
use – and modify – your own copy of the TAGE game engine distributed in class.

The game you will make is called “Dolphin Adventure 1”. The player uses the
keyboard and gamepad (Xbox controller or equivalent) to fly around in outer
space on the back of a dolphin, collecting prizes.  The prizes are scattered
randomly around the space, and to visit one, a player has to ride the dolphin
up close to the prize, get off of the dolphin, and run into it (i.e., when the
camera gets very close to the prize). This causes the prize to be collected,
and the score incremented. The player (camera) is never allowed to stray too
far from the dolphin. The score is shown on a HUD (Heads-Up Display).

## Generate Javadocs

`javadoc -d docs/ $(find . -name *.java)`

## Additional Assets Used

### Brick/Cube

- 66 high-res wall texture photos, including windows & doors - 2010-09-03_14-42-38.jpg: <https://opengameart.org/node/8484>

- Sac State logo: <https://www.csus.edu/brand/logos-logotypes.html>

### Video Copilot's iPhone 5 Object and Textures

Video Copilot: We create tools & training for people who love motion
design & visual effects!

[Video Copilot tutorial 134 - Screen Animation - iPhone5.obj download](https://www.videocopilot.net/tutorials/screen_animations/)

- [iPhone5.obj](assets/models/iPhone5.obj)
- [Iphone5_diffuse.png](assets/textures/Iphone5_diffuse.png)
- [Iphone5_Screen.png](assets/textures/Iphone5_Screen.png)
- [Iphone5_specular.png](assets/textures/Iphone5_specular.png)
