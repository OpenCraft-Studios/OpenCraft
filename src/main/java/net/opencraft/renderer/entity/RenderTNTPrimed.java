package net.opencraft.renderer.entity;

import static org.lwjgl.opengl.GL11.*;

import net.opencraft.blocks.Block;
import net.opencraft.entity.EntityTNTPrimed;

public class RenderTNTPrimed extends Render<EntityTNTPrimed> {

	private RenderBlocks blockRenderer;

	public RenderTNTPrimed() {
		this.blockRenderer = new RenderBlocks();
		this.shadowSize = 0.5f;
	}

	public void doRender(final EntityTNTPrimed entityLiving, final double xCoord, final double sqrt_double, final double yCoord, final float nya1, final float nya2) {
		glPushMatrix();
		glTranslatef((float) xCoord, (float) sqrt_double, (float) yCoord);
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
			glScalef(n2, n2, n2);
		}
		float n = (1.0f - (entityLiving.fuse - nya2 + 1.0f) / 100.0f) * 0.8f;
		this.loadTexture("/assets/terrain.png");
		this.blockRenderer.renderBlockOnInventory(Block.tnt);
		if (entityLiving.fuse / 5 % 2 == 0) {
			glDisable(3553);
			glDisable(2896);
			glEnable(3042);
			glBlendFunc(770, 772);
			glColor4f(1.0f, 1.0f, 1.0f, n);
			this.blockRenderer.renderBlockOnInventory(Block.tnt);
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			glDisable(3042);
			glEnable(GL_LIGHTING);
			glEnable(3553);
		}
		glPopMatrix();
	}

}
