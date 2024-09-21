
package net.opencraft.client.renderer.entity;

import java.util.Random;
import net.opencraft.EnumArt;
import net.opencraft.client.renderer.Tessellator;
import net.opencraft.entity.EntityPainting;
import net.opencraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderPainting extends Render<EntityPainting> {

    private Random rand;

    public RenderPainting() {
        this.rand = new Random();
    }

    public void doRender(final EntityPainting entityLiving, final double xCoord, final double sqrt_double, final double yCoord, final float nya1, final float nya2) {
        this.rand.setSeed(187L);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) xCoord, (float) sqrt_double, (float) yCoord);
        GL11.glRotatef(nya1, 0.0f, 1.0f, 0.0f);
        GL11.glEnable(32826);
        this.loadTexture("/assets/art/kz.png");
        final EnumArt art = entityLiving.art;
        final float n = 0.0625f;
        GL11.glScalef(n, n, n);
        this.func_159_a(entityLiving, art.sizeX, art.sizeY, art.offsetX, art.offsetY);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    private void func_159_a(final EntityPainting cy, final int integer2, final int integer3, final int integer4, final int integer5) {
        final float n = -integer2 / 2.0f;
        final float n2 = -integer3 / 2.0f;
        final float n3 = -0.5f;
        final float n4 = 0.5f;
        for (int i = 0; i < integer2 / 16; ++i) {
            for (int j = 0; j < integer3 / 16; ++j) {
                final float n5 = n + (i + 1) * 16;
                final float n6 = n + i * 16;
                final float n7 = n2 + (j + 1) * 16;
                final float n8 = n2 + j * 16;
                this.func_160_a(cy, (n5 + n6) / 2.0f, (n7 + n8) / 2.0f);
                final float n9 = (integer4 + integer2 - i * 16) / 256.0f;
                final float n10 = (integer4 + integer2 - (i + 1) * 16) / 256.0f;
                final float n11 = (integer5 + integer3 - j * 16) / 256.0f;
                final float n12 = (integer5 + integer3 - (j + 1) * 16) / 256.0f;
                final float n13 = 0.75f;
                final float n14 = 0.8125f;
                final float n15 = 0.0f;
                final float n16 = 0.0625f;
                final float n17 = 0.75f;
                final float n18 = 0.8125f;
                final float n19 = 0.001953125f;
                final float n20 = 0.001953125f;
                final float n21 = 0.7519531f;
                final float n22 = 0.7519531f;
                final float n23 = 0.0f;
                final float n24 = 0.0625f;
                final Tessellator instance = Tessellator.instance;
                instance.startDrawingQuads();
                instance.setNormal(0.0f, 0.0f, -1.0f);
                instance.addVertexWithUV(n5, n8, n3, n10, n11);
                instance.addVertexWithUV(n6, n8, n3, n9, n11);
                instance.addVertexWithUV(n6, n7, n3, n9, n12);
                instance.addVertexWithUV(n5, n7, n3, n10, n12);
                instance.setNormal(0.0f, 0.0f, 1.0f);
                instance.addVertexWithUV(n5, n7, n4, n13, n15);
                instance.addVertexWithUV(n6, n7, n4, n14, n15);
                instance.addVertexWithUV(n6, n8, n4, n14, n16);
                instance.addVertexWithUV(n5, n8, n4, n13, n16);
                instance.setNormal(0.0f, -1.0f, 0.0f);
                instance.addVertexWithUV(n5, n7, n3, n17, n19);
                instance.addVertexWithUV(n6, n7, n3, n18, n19);
                instance.addVertexWithUV(n6, n7, n4, n18, n20);
                instance.addVertexWithUV(n5, n7, n4, n17, n20);
                instance.setNormal(0.0f, 1.0f, 0.0f);
                instance.addVertexWithUV(n5, n8, n4, n17, n19);
                instance.addVertexWithUV(n6, n8, n4, n18, n19);
                instance.addVertexWithUV(n6, n8, n3, n18, n20);
                instance.addVertexWithUV(n5, n8, n3, n17, n20);
                instance.setNormal(-1.0f, 0.0f, 0.0f);
                instance.addVertexWithUV(n5, n7, n4, n22, n23);
                instance.addVertexWithUV(n5, n8, n4, n22, n24);
                instance.addVertexWithUV(n5, n8, n3, n21, n24);
                instance.addVertexWithUV(n5, n7, n3, n21, n23);
                instance.setNormal(1.0f, 0.0f, 0.0f);
                instance.addVertexWithUV(n6, n7, n3, n22, n23);
                instance.addVertexWithUV(n6, n8, n3, n22, n24);
                instance.addVertexWithUV(n6, n8, n4, n21, n24);
                instance.addVertexWithUV(n6, n7, n4, n21, n23);
                instance.draw();
            }
        }
    }

    private void func_160_a(final EntityPainting cy, final float float2, final float float3) {
        int nya1 = MathHelper.floor_double(cy.posX);
        final int floor_double = MathHelper.floor_double(cy.posY + float3 / 16.0f);
        int nya2 = MathHelper.floor_double(cy.posZ);
        if (cy.direction == 0) {
            nya1 = MathHelper.floor_double(cy.posX + float2 / 16.0f);
        }
        if (cy.direction == 1) {
            nya2 = MathHelper.floor_double(cy.posZ - float2 / 16.0f);
        }
        if (cy.direction == 2) {
            nya1 = MathHelper.floor_double(cy.posX - float2 / 16.0f);
        }
        if (cy.direction == 3) {
            nya2 = MathHelper.floor_double(cy.posZ + float2 / 16.0f);
        }
        final float lightBrightness = this.renderManager.worldObj.getLightBrightness(nya1, floor_double, nya2);
        GL11.glColor3f(lightBrightness, lightBrightness, lightBrightness);
    }
}
