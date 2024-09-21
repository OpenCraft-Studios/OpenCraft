
package net.opencraft.client.renderer.entity;

import net.opencraft.entity.EntityTNTPrimed;
import net.opencraft.block.Block;
import org.lwjgl.opengl.GL11;

public class RenderTNTPrimed extends Render<EntityTNTPrimed> {

    private RenderBlocks blockRenderer;

    public RenderTNTPrimed() {
        this.blockRenderer = new RenderBlocks();
        this.shadowSize = 0.5f;
    }

    public void doRender(final EntityTNTPrimed entityLiving, final double xCoord, final double sqrt_double, final double yCoord, final float nya1, final float nya2) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) xCoord, (float) sqrt_double, (float) yCoord);
        if (entityLiving.fuse - nya2 + 1.0f < 10.0f) {
            float n = 1.0f - (entityLiving.fuse - nya2 + 1.0f) / 10.0f;
            if (n < 0.0f) {
                n = 0.0f;
            }
            if (n > 1.0f) {
                n = 1.0f;
            }
            n *= n;
            n *= n;
            final float n2 = 1.0f + n * 0.3f;
            GL11.glScalef(n2, n2, n2);
        }
        float n = (1.0f - (entityLiving.fuse - nya2 + 1.0f) / 100.0f) * 0.8f;
        this.loadTexture("/assets/terrain.png");
        this.blockRenderer.renderBlockOnInventory(Block.tnt);
        if (entityLiving.fuse / 5 % 2 == 0) {
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 772);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, n);
            this.blockRenderer.renderBlockOnInventory(Block.tnt);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
        GL11.glPopMatrix();
    }
}
