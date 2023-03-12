# CSc 165 -- Spring 2023  

Technical topics, addendum's, discoveries, hints

___

3/11-- There is a small but potentially impactful TAGE bug in the lookAt()
function in the Camera class. The fix is simple. In the Camera.java file,
in the lookAt(float x, float y, float z) function,

change:

```java
if (n.equals(0,1,0)) 
```

to:  

```java
if ((n.equals(0,1,0)) || (n.equals(0,-1,0)))
```

___

2/10-- If your buttons, keys, and joysticks are not working, despite being
successfully associated with Action classes, make sure that your `update()`
function is sending the message "`im.update((float)elapsedTime);`"
to the input manager. This is shown in the input actions code sample.
That is the command that tells the input manager to actually process the
inputs in the queues.

___

2/10 -- You can access the magnitude of a joystick input from the "Event"
object that is passed to the `performAction()` method. For example:

```java
public void performAction(float time, Event e)
{ if (e.getValue() < -0.1) etc...
```

If the joystick was moved in the negative direction, `e.getValue()` would
return a negative value. The more the joystick is moved to the left,
the greater the magnitude of `e.getValue()`.

___

2/17 -- There is a bug in TAGE that can sometimes cause a viewport to not appear.  
The fix is simple. In the RenderSystem.java file,

change:

```java
private HashMap< String, Viewport> viewportList = new HashMap<>(); 
```

to:  

```java
private LinkedHashMap< String, Viewport> viewportList = new LinkedHashMap< String, Viewport>();
```

___

1/27 - If you are having trouble getting TAGE or the dolphin program to compile
or run, here are some things to try:

- The version of Java may be inconsistent. The Oracle installer often fails to
update the javapath entry used in the Path environment variable when installing
an older version of Java. To get this set correctly, look for the folder
containing the version that you want to use (preferably version 11) -- it is
probably in "C:/Program Files/java/jdk-11xxx/", and in there should be a folder
called "bin". Try making THAT the first entry in your PATH environment variable
(rather than the "javapath" entry that Oracle likes to use) - the entire path
from c: down to "bin". You'll need to reboot after that
(or at least compile and run out of a new command prompt).

- If there are multiple versions of Java on your machine, make sure that
the "bin" entry described above is the highest entry up on your PATH listing.

- If you don't want to use Oracle's version of Java
(or are annoyed by all the personal information that Oracle asks for),
you can also use one of the OpenJDK versions of Java.
For example, there is a "ZULU" version 11 that works fine for our class.

- After getting Java squared away, you may need to get rid of older
.class files still around that were produced when you were trying to compile
TAGE with a different Java version. A good indicator of this is if some of the
compiler error messages mention something like "wrong version number".
Try going through all of the TAGE folders and removing ALL of the ".class"
files before trying to recompile. Remember that some of the folders have subfolders,
you'll need to go through all of them to catch all of the old .class files.
(it goes a little faster if you sort the folder listings by file type)

___

There is a minor issue with TAGE sometimes generating a warning message about
your OS version. The error comes from JInput, and can be ignored.

___

If you get the following error message: "no jinput-raw\_64 in java.library.path",
then it means that TAGE was unable to find the jinput DLL, because you didn't
add C:\\javagaming\\jinput\\lib to your PATH variable (or you have \\bin instead of \\lib).
If you still get the error, it could also be because one of the earlier items
in your PATH are invalid (which causes the search to terminate before reaching
the jinput entry). If you aren't sure which one is invalid,
move the jinput entry earlier in the PATH list.

___

One student reported that his problems getting jinput to work were caused by
Norton Antivirus. It had deleted several of the files from the jinput/lib
folder when he downloaded javagaming.

___

Some power-saving laptops use the onboard Intel GPU by default, EVEN IF a more
powerful card (e.g., NVidia) also exists on the machine. Usually, in these cases,
there is a right-click "Run with graphics card" context menu available.
If your machine has this, you can run a command prompt with the graphics card,
then launch your game from that command window, to get it to use the better
graphics card (typically nVidia - in fact I think it is the nVidia driver that
adds the context command). If you have a newer laptop, this could very well be
worth trying. Some laptops accomplish the same thing by requiring going into
the NVidia control panel, and under "3D options" selecting "force NVidia GPU",
otherwise it uses the Intel GPU. On some machines the steps differ.

___

This semester we are using Windows 10 and Java 11. It may be possible to get
Java 8 to work, but I cannot yet support Windows 11 or versions of Java higher
than 11. A couple of the libraries we are relying on are not yet working
properly with those.
