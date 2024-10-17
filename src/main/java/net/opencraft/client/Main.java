package net.opencraft.client;

import static net.opencraft.OpenCraft.*;
import static net.opencraft.SharedConstants.*;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;

import net.opencraft.OpenCraft;

public class Main {

	private Main() {
	}

	public static void main(String[] args) throws Exception {
		checkMemoryCapacity();

		System.out.println("Starting...");
		System.setProperty("user.dir", RESOURCES.resourcesRoot.getAbsolutePath());

		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));

		oc = new OpenCraft(854, 480);
		oc.run();
	}

	private static void checkMemoryCapacity() {
		long mbMaxMemory = Runtime.getRuntime().maxMemory() / 1048576L;
		if (mbMaxMemory < 747) {
			System.err.println("OpenCraft can't run with less than 747 MB!");
			System.exit(1);
		}

		if (mbMaxMemory < 1500) {
			System.err.println("---------------------------------------------------------------------");
			System.err.println("  OpenCraft average ram consume is about 747 MB when playing.");
			System.err.println("  The amount of maximum heap you selected is " + mbMaxMemory + "MB");
			System.err.println("  The recommended amount of ram is at least 1500 MB (1.5 GB)");
			System.err.println("  If capable, please, select 1.5GB to ensure a good game behavior");
			System.err.println("---------------------------------------------------------------------");
		}
	}

}
