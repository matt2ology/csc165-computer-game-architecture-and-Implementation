# CSc 165 -- Spring 2023  

Technical topics, addendums, discoveries, hints

___

There is a minor issue with TAGE sometimes generating a warning message about your OS version. The error comes from JInput, and can be ignored.

___

If you get the following error message: "no jinput-raw\_64 in java.library.path", then it means that TAGE was unable to find the jinput DLL, because you didn't add C:\\javagaming\\jinput\\lib to your PATH variable (or you have \\bin instead of \\lib). If you still get the error, it could also be because one of the earlier items in your PATH are invalid (which causes the search to terminate before reaching the jinput entry). If you aren't sure which one is invalid, move the jinput entry earlier in the PATH list.

___

One student reported that his problems getting jinput to work were caused by Norton Antivirus. It had deleted several of the files from the jinput/lib folder when he downloaded javagaming.

___

Some power-saving laptops use the onboard Intel GPU by default, EVEN IF a more powerful card (e.g., NVidia) also exists on the machine. Usually, in these cases, there is a right-click "Run with graphics card" context menu available. If your machine has this, you can run a command prompt with the graphics card, then launch your game from that command window, to get it to use the better graphics card (typically nVidia - in fact I think it is the nVidia driver that adds the context command). If you have a newer laptop, this could very well be worth trying. Some laptops accomplish the same thing by requiring going into the NVidia control panel, and under "3D options" selecting "force NVidia GPU", otherwise it uses the Intel GPU. On some machines the steps differ.

___

This semester we are using Windows 10 and Java 11. It may be possible to get Java 8 to work, but I cannot yet support Windows 11 or versions of Java higher than 11. A couple of the libraries we are relying on are not yet working properly with those.
