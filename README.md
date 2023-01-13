# CSC 165: Computer Game Architecture and Implementation

Prerequisite(s): CSC 130; CSC 133; MATH 26A or MATH 30; PHYS 5A or PHYS 11A.

Term Typically Offered: Spring only

Days/Times: MoWeFr 2:00PM - 2:50PM

## Description: Computer Game Architecture and Implementation (3 Units)

Instructor: [Dr. V. Scott Gordon](https://www.amazon.com/stores/V.-Scott-Gordon/author/B01MU73557?ref=ap_rdr&store_ref=ap_rdr&isDramIntegrated=true&shoppingPortalEnabled=true)

Architecture and implementation of computer game systems. Topics include game engine architecture; screen 
management and rendering control; geometric models; algorithms and data structures for spatial partitioning, 
occlusion, and collision detection; real-time interactive 3D graphics and animation techniques; behavioral 
control for autonomous characters; simulation of physical phenomena; sound and music in games; optimization 
techniques; multi-player games and networking; game development tools and environments. Substantial 
programming and project work.

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

==Can get from RVR-5029 - root of c-drive - copy those 5 folders==

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
