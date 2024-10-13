package net.opencraft.client.input;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyboardInput extends GLFWKeyCallback {

	public Set<Integer> pressedKeys = new HashSet<>();

	public KeyboardInput(long window) {
		GLFW.glfwSetKeyCallback(window, this);
	}

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (action == GLFW.GLFW_PRESS) {
			pressedKeys.add(key);
		} else if (action == GLFW.GLFW_RELEASE) {
			pressedKeys.remove(key);
		} else if (action == GLFW.GLFW_REPEAT) {
			pressedKeys.add(key);
		}
	}

}
