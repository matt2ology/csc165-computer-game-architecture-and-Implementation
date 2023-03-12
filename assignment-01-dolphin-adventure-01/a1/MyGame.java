import tage.input.action.MovePitchDownActionKeyboard;
import tage.input.action.MovePitchUpActionKeyboard;
import tage.input.action.MovePitchDownActionKeyboard;
import tage.input.action.MovePitchUpActionKeyboard;
		System.out.println("press Q or Right Joystick to pitch up");
		System.out.println("press E or Right Joystick to pitch down");
				// Q = pitch up
				inputManager.associateActionWithAllKeyboards(
						net.java.games.input.Component.Identifier.Key.Q,
						pitchUp,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				// E = pitch down
				inputManager.associateActionWithAllKeyboards(
						net.java.games.input.Component.Identifier.Key.E,
						pitchDown,
						InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
