
package net.opencraft.entity;

import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.entity.RenderManager;
import net.opencraft.util.Mth;
import net.opencraft.world.World;
import org.lwjgl.opengl.GL11;

public class EntityPickupFX extends EntityFX {

	private Entity entityToPickUp;
	private EntityLiving entityPickingUp;
	private int age = 0;
	private int maxAge = 0;
	private float yOffs;

	public EntityPickupFX(World fe2, Entity eq2, EntityLiving ka2, float f) {
		super(fe2, eq2.x, eq2.y, eq2.z, eq2.xd, eq2.yd, eq2.zd);
		this.entityToPickUp = eq2;
		this.entityPickingUp = ka2;
		this.maxAge = 3;
		this.yOffs = f;
	}

	public void renderParticle(Tessellator ag2, float f, float f2, float f3, float f4, float f5, float f6) {
		float f7 = ((float) this.age + f) / (float) this.maxAge;
		f7 *= f7;
		double d = this.entityToPickUp.x;
		double d2 = this.entityToPickUp.y;
		double d3 = this.entityToPickUp.z;
		double d4 = this.entityPickingUp.lastTickPosX + (this.entityPickingUp.x - this.entityPickingUp.lastTickPosX) * (double) f;
		double d5 = this.entityPickingUp.lastTickPosY + (this.entityPickingUp.y - this.entityPickingUp.lastTickPosY) * (double) f + (double) this.yOffs;
		double d6 = this.entityPickingUp.lastTickPosZ + (this.entityPickingUp.z - this.entityPickingUp.lastTickPosZ) * (double) f;
		double d7 = d + (d4 - d) * (double) f7;
		double d8 = d2 + (d5 - d2) * (double) f7;
		double d9 = d3 + (d6 - d3) * (double) f7;
		int n = Mth.floor_double(d7);
		int n2 = Mth.floor_double(d8 + (double) (this.yOffset / 2.0f));
		int n3 = Mth.floor_double(d9);
		float f8 = this.world.getLightBrightness(n, n2, n3);
		GL11.glColor4f((float) f8, (float) f8, (float) f8, (float) 1.0f);
		RenderManager.instance.renderEntityWithPosYaw(this.entityToPickUp, (float) (d7 -= interpPosX), (float) (d8 -= interpPosY), (float) (d9 -= interpPosZ), this.entityToPickUp.yRot, f);
	}

	public void onUpdate() {
		++this.age;
		if (this.age == this.maxAge) {
			this.setEntityDead();
		}
	}

	public int getFXLayer() {
		return 2;
	}

}
