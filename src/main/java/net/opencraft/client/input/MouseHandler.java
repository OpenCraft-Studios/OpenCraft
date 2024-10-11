package net.opencraft.client.input;

import static net.opencraft.OpenCraft.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.glfw.*;

public class MouseHandler {

	public MouseButtonCallback buttons = new MouseButtonCallback();
	public CursorPosCallback position = new CursorPosCallback();
	public ScrollCallback scroll = new ScrollCallback();

	public MouseHandler(long window) {
		glfwSetMouseButtonCallback(window, buttons);
		glfwSetCursorPosCallback(window, position);
		glfwSetScrollCallback(window, scroll);
	}

	public void poll() {
		buttons.events.clear();
	}

	public boolean isButtonPressed(int button) {
		return buttons.pressedButtons.contains(button - 1);
	}

	public enum ButtonEvent {

		BUTTON_1_PRESS,
		BUTTON_1_RELEASE,
		BUTTON_2_PRESS,
		BUTTON_2_RELEASE;

		public boolean isPressed() {
			return this == BUTTON_1_PRESS || this == BUTTON_2_PRESS;
		}

		public boolean isReleased() {
			return this == BUTTON_1_RELEASE || this == BUTTON_2_RELEASE;
		}

		public int buttonNumber() {
			return switch(this) {
				case BUTTON_1_PRESS, BUTTON_1_RELEASE -> GLFW_MOUSE_BUTTON_1;
				case BUTTON_2_PRESS, BUTTON_2_RELEASE -> GLFW_MOUSE_BUTTON_2;
			};
		}

	}

	public static final class MouseButtonCallback extends GLFWMouseButtonCallback {

		public Set<Integer> pressedButtons = new HashSet<>();
		public Set<ButtonEvent> events = new HashSet<>();
		public int mods;

		@Override
		public void invoke(long window, int button, int action, int mods) {
			this.mods = mods;
			if(action == GLFW_PRESS) {
				pressedButtons.add(button);
				if(button == GLFW_MOUSE_BUTTON_1) {
					events.add(ButtonEvent.BUTTON_1_PRESS);
				} else if(button == GLFW_MOUSE_BUTTON_2) {
					events.add(ButtonEvent.BUTTON_2_PRESS);
				}
			} else if(action == GLFW_RELEASE) {
				pressedButtons.remove(button);
				if(button == GLFW_MOUSE_BUTTON_1) {
					events.add(ButtonEvent.BUTTON_1_RELEASE);
				} else if(button == GLFW_MOUSE_BUTTON_2) {
					events.add(ButtonEvent.BUTTON_2_RELEASE);
				}
			}
		}

	}

	public static final class CursorPosCallback extends GLFWCursorPosCallback {

		public double x;
		public double y;

		public double prevX;
		public double prevY;

		@Override
		public void invoke(long window, double xpos, double ypos) {
			x = xpos;
			int[] heightBuffer = new int[1];
			glfwGetWindowSize(window, null, heightBuffer);
			y = heightBuffer[0] - ypos;
		}

		public double deltaX() {
			final double out = x - prevX;
			prevX = x;
			return out;
		}

		public double deltaY() {
			final double out = y - prevY;
			prevY = y;
			return out;
		}

	}

	static final class ScrollCallback extends GLFWScrollCallback {

		@Override
		public void invoke(long window, double deltaX, double deltaY) {
			oc.player.inventory.changeCurrentItem((int) deltaY);
		}

	}

}
