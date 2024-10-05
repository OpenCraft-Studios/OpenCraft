
package net.opencraft;

import org.lwjgl.opengl.GL;

public class OpenGlCapsChecker {

    public boolean checkARBOcclusion() {
        return GL.getCapabilities().GL_ARB_occlusion_query;
    }
}
