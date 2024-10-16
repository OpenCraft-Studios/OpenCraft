
package net.opencraft.renderer.entity;

import net.opencraft.blocks.Block;
import net.opencraft.client.entity.models.ModelBase;
import net.opencraft.client.entity.models.ModelMinecart;
import net.opencraft.entity.EntityMinecart;
import net.opencraft.util.Mth;
import net.opencraft.util.Vec3;

import static org.joml.Math.*;

import org.lwjgl.opengl.GL11;

public class RenderMinecart extends Render<EntityMinecart> {

	protected ModelBase modelMinecart;

	public RenderMinecart() {
		this.shadowSize = 0.5f;
		this.modelMinecart = new ModelMinecart();
	}

	public void doRender(final EntityMinecart entityLiving, double xCoord, double sqrt_double, double yCoord, float nya1, final float nya2) {
		GL11.glPushMatrix();
		final double double1 = entityLiving.lastTickPosX + (entityLiving.posX - entityLiving.lastTickPosX) * nya2;
		final double double2 = entityLiving.lastTickPosY + (entityLiving.posY - entityLiving.lastTickPosY) * nya2;
		final double double3 = entityLiving.lastTickPosZ + (entityLiving.posZ - entityLiving.lastTickPosZ) * nya2;
		final double double4 = 0.30000001192092896;
		final Vec3 pos = entityLiving.getPos(double1, double2, double3);
		float n = entityLiving.prevRotationPitch + (entityLiving.rotationPitch - entityLiving.prevRotationPitch) * nya2;
		if (pos != null) {
			Vec3 posOffset = entityLiving.getPosOffset(double1, double2, double3, double4);
			Vec3 posOffset2 = entityLiving.getPosOffset(double1, double2, double3, -double4);
			if (posOffset == null) {
				posOffset = pos;
			}
			if (posOffset2 == null) {
				posOffset2 = pos;
			}
			xCoord += pos.x - double1;
			sqrt_double += (posOffset.y + posOffset2.y) / 2.0 - double2;
			yCoord += pos.z - double3;
			final Vec3 addVector = posOffset2.add(-posOffset.x, -posOffset.y, -posOffset.z);
			if (addVector.length() != 0) {
				final Vec3 normalize = addVector.normalize();
				nya1 = (float) (toRadians(atan2(normalize.z, normalize.x)));
				n = (float) (Math.atan(normalize.y) * 73.0);
			}
		}
		GL11.glTranslatef((float) xCoord, (float) sqrt_double, (float) yCoord);
		GL11.glRotatef(180.0f - nya1, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-n, 0.0f, 0.0f, 1.0f);
		final float float1 = entityLiving.minecartTimeSinceHit - nya2;
		float n2 = entityLiving.minecartCurrentDamage - nya2;
		if (n2 < 0.0f) {
			n2 = 0.0f;
		}
		if (float1 > 0.0f) {
			GL11.glRotatef(sin(float1) * float1 * n2 / 10.0f * entityLiving.minecartRockDirection, 1.0f, 0.0f, 0.0f);
		}
		this.loadTexture("/assets/terrain.png");
		final float n3 = 0.75f;
		GL11.glScalef(n3, n3, n3);
		new RenderBlocks().renderBlockOnInventory(Block.chest);
		GL11.glScalef(1.0f / n3, 1.0f / n3, 1.0f / n3);
		this.loadTexture("/assets/item/cart.png");
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		this.modelMinecart.render(0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
		GL11.glPopMatrix();
	}

}
