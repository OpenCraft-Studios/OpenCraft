
package net.opencraft.client.renderer.entity;

import net.opencraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderEntity extends Render {

    @Override
    public void doRender(final Entity entityLiving, final double xCoord, final double sqrt_double, final double yCoord, final float nya1, final float nya2) {
        GL11.glPushMatrix();
        Render.renderOffsetAABB(entityLiving.boundingBox, xCoord - entityLiving.lastTickPosX, sqrt_double - entityLiving.lastTickPosY, yCoord - entityLiving.lastTickPosZ);
        GL11.glPopMatrix();
    }
}
