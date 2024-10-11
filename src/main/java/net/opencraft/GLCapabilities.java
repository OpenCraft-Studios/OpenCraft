
package net.opencraft;

import org.lwjgl.opengl.GL;

public class GLCapabilities {

	private GLCapabilities() {
	}

	public static boolean checkARBOcclusion() {
		return GL.getCapabilities().GL_ARB_occlusion_query;
	}

}
