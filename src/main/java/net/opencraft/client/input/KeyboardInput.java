package net.opencraft.client.input;

import net.opencraft.client.config.GameSettings;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.glfwGetKeyName;

public class KeyboardInput extends GLFWKeyCallback {

	public Set<Integer> pressedKeys =  new HashSet<>();
	public int mods;

	public KeyboardInput(long window) {
		GLFW.glfwSetKeyCallback(window, this);
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
	}

}
