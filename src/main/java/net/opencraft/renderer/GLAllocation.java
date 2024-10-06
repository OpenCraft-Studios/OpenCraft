
package net.opencraft.renderer;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class GLAllocation {

    private static List<Integer> displayLists = new ArrayList<>();
    private static List<Integer> textureIDs = new ArrayList<>();

    public static synchronized int generateDisplayLists(final int integer) {
        final int glGenLists = GL11.glGenLists(integer);
        GLAllocation.displayLists.add(glGenLists);
        GLAllocation.displayLists.add(integer);
        return glGenLists;
    }

    /**
     * Generates "unused" texture IDs and stores them in the provided IntBuffer
     */
    public static synchronized void generateDisplayLists(final IntBuffer intBuffer) {
        GL11.glGenTextures(intBuffer);
        for(int i = intBuffer.position(); i < intBuffer.limit(); ++i) {
            GLAllocation.textureIDs.add(intBuffer.get(i));
        }
    }

    public static synchronized void deleteTexturesAndDisplayLists() {
        for (int i = 0; i < GLAllocation.displayLists.size(); i += 2) {
            GL11.glDeleteLists(GLAllocation.displayLists.get(i), GLAllocation.displayLists.get(i + 1));
        }
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(GLAllocation.textureIDs.size());
        intBuffer.flip();
        GL11.glDeleteTextures(intBuffer);
        for (int j = 0; j < GLAllocation.textureIDs.size(); ++j) {
            intBuffer.put(GLAllocation.textureIDs.get(j));
        }
        intBuffer.flip();
        GL11.glDeleteTextures(intBuffer);
        GLAllocation.displayLists.clear();
        GLAllocation.textureIDs.clear();
    }

}
