package net.opencraft.renderer.entity;

import static org.lwjgl.opengl.GL11.*;

import net.opencraft.blocks.Block;
import net.opencraft.entity.EntityFallingSand;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class RenderFallingSand extends Render<EntityFallingSand> {

	private RenderBlocks field_197_d;

	public RenderFallingSand() {
		this.field_197_d = new RenderBlocks();
		this.shadowSize = 0.5f;
	}

	public void doRender(final EntityFallingSand entityLiving, final double xCoord, final double sqrt_double, final double yCoord, final float nya1, final float nya2) {
		glPushMatrix();
		glTranslatef((float) xCoord, (float) sqrt_double, (float) yCoord);
		this.loadTexture("/assets/terrain.png");
		final Block gs = Block.BLOCKS[entityLiving.blockID];
		final World world = entityLiving.getWorld();
		glDisable(2896);
		this.field_197_d.renderBlockFallingSand(gs, world, Mth.floor_double(entityLiving.x), Mth.floor_double(entityLiving.y), Mth.floor_double(entityLiving.z));
		glEnable(GL_LIGHTING);
		glPopMatrix();
	}

}
