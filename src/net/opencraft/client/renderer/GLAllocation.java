
package net.opencraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class GLAllocation {

    private static List displayLists;
    private static List textureNames;

    public static synchronized int generateDisplayLists(final int integer) {
        final int glGenLists = GL11.glGenLists(integer);
        GLAllocation.displayLists.add(glGenLists);
        GLAllocation.displayLists.add(integer);
        return glGenLists;
    }

    public static synchronized void generateDisplayLists(final IntBuffer intBuffer) {
        GL11.glGenTextures(intBuffer);
        for (int i = intBuffer.position(); i < intBuffer.limit(); ++i) {
            GLAllocation.textureNames.add(intBuffer.get(i));
        }
    }

    public static synchronized void deleteTexturesAndDisplayLists() {
        for (int i = 0; i < GLAllocation.displayLists.size(); i += 2) {
            GL11.glDeleteLists((int) GLAllocation.displayLists.get(i), (int) GLAllocation.displayLists.get(i + 1));
        }
        final IntBuffer intBuffer = createIntBuffer(GLAllocation.textureNames.size());
        intBuffer.flip();
        GL11.glDeleteTextures(intBuffer);
        for (int j = 0; j < GLAllocation.textureNames.size(); ++j) {
            intBuffer.put((int) GLAllocation.textureNames.get(j));
        }
        intBuffer.flip();
        GL11.glDeleteTextures(intBuffer);
        GLAllocation.displayLists.clear();
        GLAllocation.textureNames.clear();
    }

    public static synchronized ByteBuffer createDirectByteBuffer(final int integer) {
        return ByteBuffer.allocateDirect(integer).order(ByteOrder.nativeOrder());
    }

    public static IntBuffer createIntBuffer(final int integer) {
        return createDirectByteBuffer(integer << 2).asIntBuffer();
    }

    public static FloatBuffer createFloatBuffer(final int integer) {
        return createDirectByteBuffer(integer << 2).asFloatBuffer();
    }

    static {
        GLAllocation.displayLists = (List) new ArrayList();
        GLAllocation.textureNames = (List) new ArrayList();
    }
}
