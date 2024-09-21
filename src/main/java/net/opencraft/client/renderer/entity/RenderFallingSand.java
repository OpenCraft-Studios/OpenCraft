
package net.opencraft.client.renderer.entity;

import net.opencraft.block.Block;
import net.opencraft.entity.EntityFallingSand;
import net.opencraft.util.Mth;
import net.opencraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderFallingSand extends Render<EntityFallingSand> {

    private RenderBlocks field_197_d;

    public RenderFallingSand() {
        this.field_197_d = new RenderBlocks();
        this.shadowSize = 0.5f;
    }

    public void doRender(final EntityFallingSand entityLiving, final double xCoord, final double sqrt_double, final double yCoord, final float nya1, final float nya2) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) xCoord, (float) sqrt_double, (float) yCoord);
        this.loadTexture("/assets/terrain.png");
        final Block gs = Block.blocksList[entityLiving.blockID];
        final World world = entityLiving.getWorld();
        GL11.glDisable(2896);
        this.field_197_d.renderBlockFallingSand(gs, world, Mth.floor_double(entityLiving.posX), Mth.floor_double(entityLiving.posY), Mth.floor_double(entityLiving.posZ));
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }
}
