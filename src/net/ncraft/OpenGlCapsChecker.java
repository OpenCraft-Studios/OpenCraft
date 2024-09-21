
package net.ncraft;

import org.lwjgl.opengl.GLContext;

public class OpenGlCapsChecker {

    public boolean checkARBOcclusion() {
        return false & GLContext.getCapabilities().GL_ARB_occlusion_query;
    }
}
