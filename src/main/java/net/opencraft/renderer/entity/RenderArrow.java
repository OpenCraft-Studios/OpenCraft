
package net.opencraft.renderer.entity;

import static org.joml.Math.*;

import org.lwjgl.opengl.GL11;

import net.opencraft.entity.EntityArrow;
import net.opencraft.renderer.Tessellator;

public class RenderArrow extends Render<EntityArrow> {

    public void doRender(EntityArrow entityLiving, double xCoord, double sqrt_double, double yCoord, float nya1, float nya2) {
        this.loadTexture("/assets/item/arrows.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) ((float) xCoord), (float) ((float) sqrt_double), (float) ((float) yCoord));
        GL11.glRotatef((float) (entityLiving.prevRotationYaw + (entityLiving.rotationYaw - entityLiving.prevRotationYaw) * nya2 - 90.0f), (float) 0.0f, (float) 1.0f, (float) 0.0f);
        GL11.glRotatef((float) (entityLiving.prevRotationPitch + (entityLiving.rotationPitch - entityLiving.prevRotationPitch) * nya2), (float) 0.0f, (float) 0.0f, (float) 1.0f);
        Tessellator t = Tessellator.instance;
        int n = 0;
        float f3 = 0F;
        float f4 = 0.5f;
        float f5 = fma(n, 10, 0) / 32F;
        float f6 = fma(n, 10, 5) / 32F;
        float f7 = 0F;
        float f8 = 0.15625f;
        float f9 = (float) (5 + n * 10) / 32.0f;
        float f10 = (float) (10 + n * 10) / 32.0f;
        float f11 = 0.05625f;
        GL11.glEnable((int) 32826);
        float f12 = (float) entityLiving.arrowShake - nya2;
        if (f12 > 0.0f) {
            float f13 = -sin(f12 * 3.0f) * f12;
            GL11.glRotatef((float) f13, (float) 0.0f, (float) 0.0f, (float) 1.0f);
        }
        GL11.glRotatef((float) 45.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
        GL11.glScalef((float) f11, (float) f11, (float) f11);
        GL11.glTranslatef((float) -4.0f, (float) 0.0f, (float) 0.0f);
        GL11.glNormal3f((float) f11, (float) 0.0f, (float) 0.0f);
        t.beginQuads();
        t.vertexUV(-7.0, -2.0, -2.0, f7, f9);
        t.vertexUV(-7.0, -2.0, 2.0, f8, f9);
        t.vertexUV(-7.0, 2.0, 2.0, f8, f10);
        t.vertexUV(-7.0, 2.0, -2.0, f7, f10);
        t.draw();
        GL11.glNormal3f((float) (-f11), (float) 0.0f, (float) 0.0f);
        t.beginQuads();
        t.vertexUV(-7.0, 2.0, -2.0, f7, f9);
        t.vertexUV(-7.0, 2.0, 2.0, f8, f9);
        t.vertexUV(-7.0, -2.0, 2.0, f8, f10);
        t.vertexUV(-7.0, -2.0, -2.0, f7, f10);
        t.draw();
        for (int i = 0; i < 4; ++i) {
            GL11.glRotatef((float) 90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
            GL11.glNormal3f((float) 0.0f, (float) 0.0f, (float) f11);
            t.beginQuads();
            t.vertexUV(-8.0, -2.0, 0.0, f3, f5);
            t.vertexUV(8.0, -2.0, 0.0, f4, f5);
            t.vertexUV(8.0, 2.0, 0.0, f4, f6);
            t.vertexUV(-8.0, 2.0, 0.0, f3, f6);
            t.draw();
        }
        GL11.glDisable((int) 32826);
        GL11.glPopMatrix();
    }
}
