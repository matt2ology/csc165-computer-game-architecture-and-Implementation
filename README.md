# CSC 165: Computer Game Architecture and Implementation

Prerequisite(s): CSC 130; CSC 133; MATH 26A or MATH 30; PHYS 5A or PHYS 11A.

Term Typically Offered: Spring only

Days/Times: MoWeFr 2:00PM - 2:50PM

- [CSC 165: Computer Game Architecture and Implementation](#csc-165-computer-game-architecture-and-implementation)
  - [Description: Computer Game Architecture and Implementation (3 Units)](#description-computer-game-architecture-and-implementation-3-units)
  - [TAGE Goals](#tage-goals)
    - [Making your own game](#making-your-own-game)
  - [Setup/Installation (assumes Java 11 JDK and JOGL 2.4)](#setupinstallation-assumes-java-11-jdk-and-jogl-24)
    - [Operating System and Java](#operating-system-and-java)
      - [3rd Party Library Dependency That TAGE Relies on](#3rd-party-library-dependency-that-tage-relies-on)
      - [System Variable](#system-variable)
    - [Compile TAGE](#compile-tage)
    - [Validate Your Setup Is In Proper Order](#validate-your-setup-is-in-proper-order)
      - [Before](#before)
      - [After](#after)

## Description: Computer Game Architecture and Implementation (3 Units)

Instructor: [Dr. V. Scott Gordon](https://www.amazon.com/stores/V.-Scott-Gordon/author/B01MU73557?ref=ap_rdr&store_ref=ap_rdr&isDramIntegrated=true&shoppingPortalEnabled=true)

Architecture and implementation of computer game systems. Topics include game
engine architecture; screen management and rendering control; geometric models;
algorithms and data structures for spatial partitioning, occlusion, and collision
detection; real-time interactive 3D graphics and animation techniques; behavioral
control for autonomous characters; simulation of physical phenomena; sound and
music in games; optimization techniques; multi-player games and networking;
game development tools and environments. Substantial programming and project work.

## TAGE Goals

1. Simple engine design
2. [CSc-155 Advance Computer Graphics](https://catalog.csus.edu/courses-a-z/csc/) code style

### Making your own game

Follow the document, introGuide.pdf, provided in [tage_build.zip](tage-game-engine/tage_build.zip)
on page 2 where a template is provided.

The game's java files go in the "myGame" folder (a generic name and can be named
to your liking). The main.java file extends `VariableFrameRateGame`.

## Setup/Installation (assumes Java 11 JDK and JOGL 2.4)

Reboot your machine when all setup!

### Operating System and Java

1. Need Windows 10
2. Need Java SDK 11 (or JDK/SE) - Oracle
3. Need a graphics card that supports OpenGL 4.3
   1. [OpenGL Extensions Viewer](https://download.cnet.com/OpenGL-Extensions-Viewer/3000-18487_4-34442.html) - helps
   2. Macs only go up to 4.1, so they are not capable with this course

#### 3rd Party Library Dependency That TAGE Relies on

Having these libraries downloaded is not sufficient and will require additional
steps. We will need to add both the `CLASSPATH` and environmental `Paths` variable
and can be found in the TAGE document: [tage_build.zip](tage-game-engine/tage_build.zip)

In the document follow the instructions and add items to the `CLASSPATH`
and `Path` System variables.

**Place the following libraries into your c-drive**: C:\\javagaming

**Can get from RVR-5029 - root of c-drive - copy those 5 folders**

- Jbullet - Physics library
- JOGL - Java wrapper for OpenGL
  - tricky to find on your own; for, the class uses a beta version
  - Includes the JOLE library (Audio)
- JOML - A math library specifically used for JOGL
- Jinput - A library for input devices like gamepads
- vecmath - Math library for animation code

#### System Variable

If `CLASSPATH` System variable does not exist do the following:

1. Click "New..."
2. Simply add a dot: `.`
   1. As in current directory
3. Enter and then add the rest according to the document specified in: introGuide.pdf
   1. Jinput - include path lib into the `CLASSPATH` value: C:\\javagaming\\jinput\lib

### Compile TAGE

Run the compileTAGE.bat located in the
[tage_build.zip](tage-game-engine/tage_build.zip)

The file simply compiles the following java code:

```bat
javac tage\*.java
javac tage\input\*.java
javac tage\input\action\*.java
javac tage\networking\*.java
javac tage\networking\client\*.java
javac tage\networking\server\*.java
javac tage\nodeControllers\*.java
javac tage\shapes\*.java
javac tage\objectRenderers\*.java
javac tage\physics\*.java
javac tage\physics\JBullet\*.java
javac tage\ai\behaviortrees\*.java
javac tage\audio\*.java
javac tage\audio\joal\*.java
```

### Validate Your Setup Is In Proper Order

Run `HelloDolphin.java` found in Canvas or [instructor's website](https://athena.ecs.csus.edu/~gordonvs/)

Inside the HelloDolphin folder you'll find the following items

#### Before

- assets/
- myGame/
- compile.bat
- run.bat
- zz-ABOUT.txt

#### After

Now we want to copy the tage game engine folder into the directory
(and into future assignment projects), so we'll have the following.

TAGE will be an engine that we will be modifying over time and will be
different from initial copy and that of other classmates.

- assets/
- myGame/
- tage/
- compile.bat
- run.bat
- zz-ABOUT.txt
