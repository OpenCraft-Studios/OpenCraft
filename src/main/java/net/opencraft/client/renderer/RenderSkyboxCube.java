package net.opencraft.client.renderer;

import net.opencraft.OpenCraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class RenderSkyboxCube {
    private final String[] locations = new String[6];

    // Constructor to initialize texture locations
    public RenderSkyboxCube(String texture) {
        for (int i = 0; i < 6; ++i) {
            this.locations[i] = "/assets/gui/panorama/panorama_" + i + ".png";
        }
    }

    // Renders the skybox using Tessellator directly
    public void render(OpenCraft mc, float rotX, float rotY) {
        Tessellator tessellator = Tessellator.instance;

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        Project.gluPerspective(70.0F, (float) mc.width / (float) mc.height, 0.05F, 10.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);

        // Disable depth test and set up blending
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);

        for(int j = 0; j < 4; ++j) {
            GlStateManager.pushMatrix();
            float f = ((float) (j % 2) / 2.0F - 0.5F) / 256.0F;
            float f1 = ((float) (j / 2) / 2.0F - 0.5F) / 256.0F;
            float f2 = 0.0F;
            GL11.glTranslatef(f, f1, 0.0F);
            GL11.glRotatef(rotX, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(rotY, 0.0F, 1.0F, 0.0F);

            for (int i = 0; i < 6; ++i) {
                // Bind the texture for the current face
                mc.renderEngine.bindTexture(mc.renderEngine.getTexture(this.locations[i]));

                tessellator.beginQuads();

                if (i == 0) { // Front face
                    tessellator.vertexUV(-1.0D, -1.0D, 1.0D, 0.0D, 0.0D);
                    tessellator.vertexUV(1.0D, -1.0D, 1.0D, 1.0D, 0.0D);
                    tessellator.vertexUV(1.0D, 1.0D, 1.0D, 1.0D, 1.0D);
                    tessellator.vertexUV(-1.0D, 1.0D, 1.0D, 0.0D, 1.0D);
                } else if (i == 3) { // Left face
                    tessellator.vertexUV(-1.0D, -1.0D, -1.0D, 0.0D, 0.0D);
                    tessellator.vertexUV(-1.0D, -1.0D, 1.0D, 1.0D, 0.0D);
                    tessellator.vertexUV(-1.0D, 1.0D, 1.0D, 1.0D, 1.0D);
                    tessellator.vertexUV(-1.0D, 1.0D, -1.0D, 0.0D, 1.0D);
                } else if (i == 2) { // Back face
                    tessellator.vertexUV(1.0D, -1.0D, -1.0D, 0.0D, 0.0D);
                    tessellator.vertexUV(-1.0D, -1.0D, -1.0D, 1.0D, 0.0D);
                    tessellator.vertexUV(-1.0D, 1.0D, -1.0D, 1.0D, 1.0D);
                    tessellator.vertexUV(1.0D, 1.0D, -1.0D, 0.0D, 1.0D);
                } else if (i == 1) { // Right face
                    tessellator.vertexUV(1.0D, -1.0D, 1.0D, 0.0D, 0.0D);
                    tessellator.vertexUV(1.0D, -1.0D, -1.0D, 1.0D, 0.0D);
                    tessellator.vertexUV(1.0D, 1.0D, -1.0D, 1.0D, 1.0D);
                    tessellator.vertexUV(1.0D, 1.0D, 1.0D, 0.0D, 1.0D);
                } else if (i == 4) { // Bottom face
                    tessellator.vertexUV(-1.0D, -1.0D, -1.0D, 0.0D, 0.0D);
                    tessellator.vertexUV(1.0D, -1.0D, -1.0D, 1.0D, 0.0D);
                    tessellator.vertexUV(1.0D, -1.0D, 1.0D, 1.0D, 1.0D);
                    tessellator.vertexUV(-1.0D, -1.0D, 1.0D, 0.0D, 1.0D);
                } else if (i == 5) { // Top face
                    tessellator.vertexUV(-1.0D, 1.0D, 1.0D, 0.0D, 0.0D);
                    tessellator.vertexUV(1.0D, 1.0D, 1.0D, 1.0D, 0.0D);
                    tessellator.vertexUV(1.0D, 1.0D, -1.0D, 1.0D, 1.0D);
                    tessellator.vertexUV(-1.0D, 1.0D, -1.0D, 0.0D, 1.0D);
                }

                tessellator.draw();

            }
            GL11.glPopMatrix();
            GL11.glColorMask(true, true, true, false);
        }


        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }
}
