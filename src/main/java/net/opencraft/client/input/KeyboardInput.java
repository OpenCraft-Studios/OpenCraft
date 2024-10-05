package net.opencraft.client.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.glfwGetKeyName;

public class KeyboardInput extends GLFWKeyCallback {

	public Set<Integer> pressedKeys =  new HashSet<>();
	public Set<KeyEvent> events = new HashSet<>();
	public int mods;

	public KeyboardInput(long window) {
		GLFW.glfwSetKeyCallback(window, this);
	}

	public static class KeyEvent {
		public int key;
		public int scancode;
		public int action;
		public int mods;

		public KeyEvent(int key, int scancode, int action, int mods) {
			this.key = key;
			this.scancode = scancode;
			this.action = action;
			this.mods = mods;
		}

		public char getCharacter() {
			return glfwGetKeyName(key, scancode).charAt(0);
		}

		public boolean isDown() {
			return action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT;
		}

		public boolean isPress() {
			return action == GLFW.GLFW_PRESS;
		}

		public boolean isRelease() {
			return action == GLFW.GLFW_RELEASE;
		}

	}


	public void reset() {
		events.clear();
	}

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		this.mods = mods;
		if (action == GLFW.GLFW_PRESS) {
			pressedKeys.add(key);
		} else if (action == GLFW.GLFW_RELEASE) {
			pressedKeys.remove(key);
		} else if(action == GLFW.GLFW_REPEAT) {
			pressedKeys.add(key);
		}
		events.add(new KeyEvent(key, scancode, action, mods));
	}

}
